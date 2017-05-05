package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.xtmrest.XtmRestBaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
class NodeConnection {
    private final RestTemplate restTemplate;
    private final NodeAccessData nodeAccessData;
    private final XtmRestBaseUtil xtmRestBaseUtil;
    private int sessionId;

    NodeConnection(NodeAccessData nodeAccessData, RestTemplate restTemplate) {
        this.nodeAccessData = nodeAccessData;
        this.restTemplate = restTemplate;
        this.xtmRestBaseUtil = XtmRestBaseUtil.INSTANCE;
    }

    static NodeConnection create(NodeAccessData nodeAccessData) {
        return new NodeConnection(nodeAccessData, RestTemplateFactory.REST_TEMPLATE_FACTORY.createRestTemplate());
    }

    ResponseEntity<String> performRestAction(String mibPath) {
        if(sessionId == 0) {
            loginAndSetSessionId();
        }
        return performHttpGetRequest(mibPath, createHttpEntityWithSessionId());
    }

    /**
     * * TODO: Implement back-off policy and max nr of attempts
     */
    void loginAndSetSessionId() {
        while(this.sessionId == 0) {
            ResponseEntity<String> loginResponse = login();
            log.info("loginResponse {}", loginResponse);
            if(SessionIdParser.parseSessionId(loginResponse.getBody()) == 0) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                this.sessionId = SessionIdParser.parseSessionId(loginResponse.getBody());
            }
        }
    }

    int getSessionId() {
        return this.sessionId;
    }

    private ResponseEntity<String> login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        String loginPath = xtmRestBaseUtil.loginPath(nodeAccessData.getUserName(), nodeAccessData.getPassword());
        return performHttpGetRequest(loginPath, httpEntity);
    }

    /**
     * TODO: Implement back-off policy and max nr of attempts
     * Connection timeout is set to 3000 ms in createRestTemplate(). When time out occurs
     * we catch exception, sleeps for a while and tries again.
     */
    private ResponseEntity<String> performHttpGetRequest(String path, HttpEntity httpEntity) {
        String baseUrl = xtmRestBaseUtil.baseUrl(nodeAccessData.getIpAddress(), nodeAccessData.getPort());
        String url = xtmRestBaseUtil.url(baseUrl, path);
        ResponseEntity<String> responseEntity = null;
        int nrOfAttempts = 0;
        while (responseEntity == null) {
            try {
                log.info("Performing http GET with url {}", url);
                responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        httpEntity,
                        String.class
                );
            } catch (Exception e) {
                log.error("Failed to perform http GET {} (Try nr {})\n{}", url, e.getMessage());
            }
        }
        return responseEntity;
    }

    private HttpEntity createHttpEntityWithSessionId() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "sessionId="+sessionId);
        return new HttpEntity<String>(headers);
    }
}

