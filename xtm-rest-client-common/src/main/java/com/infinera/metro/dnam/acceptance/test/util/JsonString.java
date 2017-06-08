package com.infinera.metro.dnam.acceptance.test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonString {
    private final Object object;
    final ObjectMapper mapper = new ObjectMapper();

    public JsonString(Object object) {
        this.object = object;
    }

    @Override
    public String toString() throws RuntimeException {
        try {
            final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
