package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public enum ObjectFromFileUtil {
    INSTANCE;

    public <T> T getObject(String path, Class<T> clazz) throws RuntimeException {
        try {
            ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(clazz);
            InputStream in = new ResourceInputStream(path);
            return reader.readValue(in);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
