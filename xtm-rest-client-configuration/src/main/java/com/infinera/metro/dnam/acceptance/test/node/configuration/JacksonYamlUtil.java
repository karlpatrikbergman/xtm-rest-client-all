package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.infinera.metro.dnam.acceptance.test.node.mib.Board;
import com.infinera.metro.dnam.acceptance.test.node.mib.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.mib.LinePort;

public enum JacksonYamlUtil {
    INSTANCE;

    final ObjectMapper mapper;

    JacksonYamlUtil() {
        mapper = new ObjectMapper(new YAMLFactory());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Board.class, new BoardDeserializer());
        module.addDeserializer(LinePort.class, new LinePortDeserializer());
        module.addDeserializer(ClientPort.class, new ClientPortDeserializer());
        mapper.registerModule(module);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public ObjectReader getReader() {
        return mapper.reader();
    }
}
