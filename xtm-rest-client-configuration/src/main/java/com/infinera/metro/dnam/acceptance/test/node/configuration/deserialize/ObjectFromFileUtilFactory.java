package com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.ObjectFromFileUtil;

public enum  ObjectFromFileUtilFactory {
    INSTANCE;

    public ObjectFromFileUtil getObjectFromFileUtil() {
        return ObjectFromFileUtilJackson.INSTANCE;
    }
}
