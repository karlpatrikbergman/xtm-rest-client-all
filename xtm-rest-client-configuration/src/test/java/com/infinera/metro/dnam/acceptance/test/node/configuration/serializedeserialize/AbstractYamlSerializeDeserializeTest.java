package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

abstract class AbstractYamlSerializeDeserializeTest<T> {
    private final ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();

    private final Class<T> typeParameterClass;
    private final  T expectedObject;
    private final String pathToYamlFile;

    AbstractYamlSerializeDeserializeTest(Class<T> typeParameterClass, T expectedObject, String pathToYamlFile) {
        this.typeParameterClass = typeParameterClass;
        this.expectedObject = expectedObject;
        this.pathToYamlFile = pathToYamlFile;
    }

    @Test
    public void serializeObject() throws JsonProcessingException {
        final String expectedYamlString = new ResourceString(pathToYamlFile).toString();
        final String serializedObject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedObject).trim();
        assertEquals(expectedYamlString, serializedObject);
    }

    @Test
    public void deserializeObject() {
        final T deserializedObject = ObjectFromFileUtilJackson.INSTANCE.getObject(pathToYamlFile, typeParameterClass);
        assertNotNull(deserializedObject);
        assertEquals(expectedObject, deserializedObject);
    }
}
