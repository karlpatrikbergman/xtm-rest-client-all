package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

public enum  ObjectFromFileUtilFactory {
    INSTANCE;

    public ObjectFromFileUtil getObjectFromFileUtil() {
        return ObjectFromFileUtilJackson.INSTANCE;
    }
}
