package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;

import java.io.IOException;
import java.io.InputStream;

public enum ObjectFromFileUtil {
    INSTANCE;

    public <T> T getObject(String path, Class<T> clazz) throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(clazz);
        InputStream in = new ResourceInputStream(path);
        return reader.readValue(in);
    }
}
