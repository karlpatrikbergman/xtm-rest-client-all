package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.*;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public enum ObjectFromFileUtilJackson implements ObjectFromFileUtil {
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

        SimpleModule module = new SimpleModule();
        module.addDeserializer(ModuleType.class, new ModuleTypeDeserializer());
        module.addDeserializer(GroupOrTableType.class, new GroupOrTableTypeDeserializer());
        module.addDeserializer(BoardType.class, new BoardTypeDeserializer());
        module.addDeserializer(LinePortType.class, new LinePortTypeDeserializer());
        module.addDeserializer(ClientPortType.class, new ClientPortTypeDeserializer());
        mapper.registerModule(module);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
