package com.infinera.metro.dnam.acceptance.test.node.dto;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class FooSerializeDeserializeTest {

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //Normally, default ObjectMapper cannot deserialize this message into a CarInfo object.
        //With following configuration, it's possible:
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @Test
    public void deserializeFoo() throws IOException {
        String fooJsonString = new ResourceString("deserializing/foo.json").toString();
        assertNotNull(fooJsonString);
        Foo foo = mapper.readValue(fooJsonString, Foo.class);
        assertNotNull(foo);
        log.info("{}", foo);
    }
}

