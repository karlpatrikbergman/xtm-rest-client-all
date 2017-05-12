package com.infinera.metro.dnam.acceptance.test.node;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class XtmRestBaseUtilTest {
    private final XtmRestBaseUtil xtmRestBaseUtil = XtmRestBaseUtil.INSTANCE;

    @Test
    public void createBaseUrl() {
        String expectedBaseUrl = "http://172.17.0.3:80";
        String actualBaseUrl = xtmRestBaseUtil.baseUrl("172.17.0.3", 80);
        assertEquals(expectedBaseUrl, actualBaseUrl);
        log.info("Base url: {}" , actualBaseUrl);
    }

    @Test
    public void loginPath() {
        String expectedLoginPath = "/getLogin.asp?userName=root&password=root&oneTimeLogin=false";
        String actualLoginPath = xtmRestBaseUtil.loginPath("root", "root");
        assertEquals(expectedLoginPath, actualLoginPath);
        log.info("Login path: {}", actualLoginPath);
    }

    @Test
    public void createMibUrl() {
        String expectedMibUrl = "http://172.17.0.3:80/mib/eq/board/cusfp:1:1/GET.json";
        String baseUrl = xtmRestBaseUtil.baseUrl("172.17.0.3", 80);
        String mibPath = "/mib/eq/board/cusfp:1:1/GET.json";
        String actualMibUrl = xtmRestBaseUtil.url(baseUrl, mibPath);
        assertEquals(expectedMibUrl, actualMibUrl);
        log.info("Mib url: {}", actualMibUrl);
    }
}
