package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

public interface ObjectFromFileUtil {
    <T> T getObject(String path, Class<T> clazz) throws RuntimeException;
}

