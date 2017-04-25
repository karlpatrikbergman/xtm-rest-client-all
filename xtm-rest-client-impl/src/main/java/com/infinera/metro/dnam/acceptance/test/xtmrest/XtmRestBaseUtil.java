package com.infinera.metro.dnam.acceptance.test.xtmrest;

import org.stringtemplate.v4.ST;

public enum XtmRestBaseUtil {
    INSTANCE;

    public String url(String baseUrl , String mibPath) {
        ST url = new ST("<baseurl><mibRestUrl>");
        url.add("baseurl", baseUrl);
        url.add("mibRestUrl", mibPath);
        return url.render();

    }

    public String baseUrl(String ipAddress, int port) {
        ST baseUri = new ST("http://<ipaddress>:<port>");
        baseUri.add("ipaddress", ipAddress);
        baseUri.add("port", port);
        return baseUri.render();
    }

    public String loginPath(String userName, String password) {
        ST loginUrl = new ST("/getLogin.asp?userName=<username>&password=<password>&oneTimeLogin=false");
        loginUrl.add("username", userName);
        loginUrl.add("password", password);
        return loginUrl.render();
    }
}
