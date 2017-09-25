package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class AttributeObjectDeserializeTest {

    private static final ObjectReader READER = JacksonUtil.INSTANCE.getReader().forType(AttributeObject.class);

    @Test
    public void deserializeAttributeObject() throws IOException {
        final String attributeObjectJsonString = new ResourceString("deserializing/attribute-object.json").toString();
        assertNotNull(attributeObjectJsonString);

        final AttributeObject attributeObject = READER.readValue(attributeObjectJsonString);
        assertNotNull(attributeObject);

        log.info("{}", attributeObject);
    }

    @Test
    public void deserializeAttributeObjectLackingNameField() throws IOException {
        final String attributeObjectJsonString = new ResourceString("deserializing/attribute-object-lacking-name-field.json").toString();
        assertNotNull(attributeObjectJsonString);

        AttributeObject attributeObject = READER.readValue(attributeObjectJsonString);
        assertNotNull(attributeObject);

        log.info("{}", attributeObject);
    }

    @Test
    public void deserializeAttributeArray() throws IOException {
        final String attributeArrayJsonString = new ResourceString("deserializing/attribute-array.json").toString();
        assertNotNull(attributeArrayJsonString);

        final ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AttributeObject>>(){});
        List<AttributeObject> attributeArray = reader.readValue(attributeArrayJsonString);
        assertNotNull(attributeArray);

        attributeArray.forEach(attributeObject -> log.info("{}", attributeObject));
    }
}

