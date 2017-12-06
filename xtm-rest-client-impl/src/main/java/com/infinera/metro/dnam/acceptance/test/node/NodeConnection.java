package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.util.ThreadSleepWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

import static com.infinera.metro.dnam.acceptance.test.node.SessionIdParser.NODE_IS_STARTING_UP;
import static com.infinera.metro.dnam.acceptance.test.node.SessionIdParser.SESSION_ID_PARSER;

@Slf4j
class
NodeConnection {
    private final RestTemplate restTemplate;
    private final NodeAccessData nodeAccessData;
    private final XtmRestBaseUtil xtmRestBaseUtil;
    private String sessionId = "";

    NodeConnection(NodeAccessData nodeAccessData, RestTemplate restTemplate) {
        this.nodeAccessData = nodeAccessData;
        this.restTemplate = restTemplate;
        this.xtmRestBaseUtil = XtmRestBaseUtil.INSTANCE;
    }

    ResponseEntity<String> performRestAction(String mibPath) throws RuntimeException {
        if(sessionId.isEmpty()) {
            loginAndSetSessionId();
        }
        return performHttpGetRequest(mibPath, createHttpEntityWithSessionId());
    }

    /**
     * * TODO: Implement back-off policy and max nr of attempts
     */
    void loginAndSetSessionId() {
        while(sessionId.isEmpty()) {
            ResponseEntity<String> loginResponse = login();
            if(SESSION_ID_PARSER.parseSessionId(loginResponse.getBody()) == NODE_IS_STARTING_UP) {
                try {
                    log.info("Sleeping for 2 seconds");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            } else {
                this.sessionId = SESSION_ID_PARSER.parseSessionId(loginResponse.getBody());
            }
        }
    }

    String getSessionId() {
        return this.sessionId;
    }

    String getNodeIpAddress() {
        return nodeAccessData.getIpAddress();
    }

    private ResponseEntity<String> login() throws RuntimeException {
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
    private ResponseEntity<String> performHttpGetRequest(String path, HttpEntity httpEntity) throws RuntimeException {
        String baseUrl = xtmRestBaseUtil.baseUrl(nodeAccessData.getIpAddress(), nodeAccessData.getPort());
        String url = xtmRestBaseUtil.url(baseUrl, path);
        int attempts = 0;
        int maxAttempts = 10;
        RestClientException lastAttemptException = null;
        while(attempts++ < maxAttempts) {
            try {
                log.info("Performing http GET with url {}, sessionId {}", url, sessionId);
                return restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        httpEntity,
                        String.class
                );
            } catch (RestClientException e) {
                log.error("Failed to perform http GET {} (Try nr {})\n{}", url, e.getMessage());
                ThreadSleepWrapper.sleep(5);
                lastAttemptException = e;
            }
        }
        throw new RuntimeException("Failed to perform http request "+maxAttempts+" times. Exception: " + lastAttemptException);


//
//        int nrOfAttempts = 0;
//        while (responseEntity == null) {
//            try {
//                log.info("Performing http GET with url {}", url);
//                responseEntity = restTemplate.exchange(
//                        url,
//                        HttpMethod.GET,
//                        httpEntity,
//                        String.class
//                );
//            } catch (Exception e) {
//                log.error("Failed to perform http GET {} (Try nr {})\n{}", url, e.getMessage());
//            }
//        }
//        return responseEntity;
    }

    private HttpEntity createHttpEntityWithSessionId() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "sessionId="+sessionId);
        return new HttpEntity<String>(headers);
    }
}

