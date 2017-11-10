package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

//TODO: Rename
@Slf4j
public enum ObjectFromFileUtilJackson {
    INSTANCE;

    private final ObjectMapper mapper;

    public <T> T getObject(String path, Class<T> clazz) throws RuntimeException {
        try {
            ObjectReader reader = mapper.reader().forType(clazz);
            InputStream in = new ResourceInputStream(path);
            return reader.readValue(in);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    ObjectFromFileUtilJackson() {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(mapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public String objectToString(Object object) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object).trim();
    }
}
