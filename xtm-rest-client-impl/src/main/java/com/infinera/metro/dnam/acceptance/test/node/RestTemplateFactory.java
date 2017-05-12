package com.infinera.metro.dnam.acceptance.test.node;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

enum RestTemplateFactory {
    REST_TEMPLATE_FACTORY;

    RestTemplate createRestTemplate() {
        int timeout = 3000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return new RestTemplate(clientHttpRequestFactory);
    }
}
