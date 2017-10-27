package com.infinera.metro.dnam.acceptance.test.node.mib.util;

public enum  PortUtil {
    PORT_UTIL;

    //TODO: I don't know if it is correct to assume that an even port number implies send.
    //TODO: Reverse in this case assumes the connection is symmetric. Should this be clarified in the method name?
    public Integer reversePort(Integer port) {
        return (isTransmitPort(port) ? port + 1 : port - 1);
    }

    private boolean isTransmitPort(Integer port) {
        return (port % 2 != 0);
    }
}
