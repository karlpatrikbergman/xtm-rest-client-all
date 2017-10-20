package com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize;

public enum  ObjectFromFileUtilFactory {
    INSTANCE;

    public ObjectFromFileUtil getObjectFromFileUtil() {
        return ObjectFromFileUtilJackson.INSTANCE;
    }
}
