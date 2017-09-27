package com.infinera.metro.dnam.acceptance.test.node.mib.util;

public enum  PortUtil {
    PORT_UTIL;

    //Todo: put in util class. I don't know if it is correct to assume that an even port number implies send.
    public Integer reversePort(Integer port) {
        return (isTransmitPort(port) ? port + 1 : port - 1);
    }

    public boolean isTransmitPort(Integer port) {
        return (port % 2 != 0);
    }
}
