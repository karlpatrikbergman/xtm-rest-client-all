package com.infinera.metro.dnam.acceptance.test.node.configuration;

public enum  ObjectFromFileUtilFactory {
    INSTANCE;

    public static ObjectFromFileUtil getObjectFromFileUtil() {
        return ObjectFromFileUtilJackson.INSTANCE;
    }
}
