package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

enum JacksonUtil {
    INSTANCE;

    final ObjectMapper mapper;

    JacksonUtil() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttributeObject.class, new AttributeObjectDeserializer());
        module.addDeserializer(AnswerObject.class, new AnswerObjectDeserializer());
        mapper.registerModule(module);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    ObjectReader getReader() {
        return mapper.reader();
    }
}
