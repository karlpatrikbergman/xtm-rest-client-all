package com.infinera.metro.dnam.acceptance.test.node.configuration;

public interface ObjectFromFileUtil {
    <T> T getObject(String path, Class<T> clazz) throws RuntimeException;
}

