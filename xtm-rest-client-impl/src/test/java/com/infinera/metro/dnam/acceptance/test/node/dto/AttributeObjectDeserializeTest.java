package com.infinera.metro.dnam.acceptance.test.node.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.dto.deserializer.JacksonUtil;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class AttributeObjectDeserializeTest {

    @Test
    public void deserializeAttributeObject() throws IOException {
        String attributeObjectJsonString = new ResourceString("deserializing/attribute-object.json").toString();
        assertNotNull(attributeObjectJsonString);

        ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(AttributeObject.class);
        AttributeObject attributeObject = reader.readValue(attributeObjectJsonString);
        assertNotNull(attributeObject);

        log.info("{}", attributeObject);
    }

    @Test
    public void deserializeAttributeObjectLackingNameField() throws IOException {
        String attributeObjectJsonString = new ResourceString("deserializing/attribute-object-lacking-name-field.json").toString();
        assertNotNull(attributeObjectJsonString);

        ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(AttributeObject.class);
        AttributeObject attributeObject = reader.readValue(attributeObjectJsonString);
        assertNotNull(attributeObject);

        log.info("{}", attributeObject);
    }

    @Test
    public void deserializeAttributeArray() throws IOException {
        String attributeArrayJsonString = new ResourceString("deserializing/attribute-array.json").toString();
        assertNotNull(attributeArrayJsonString);

        ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AttributeObject>>(){});
        List<AttributeObject> attributeArray = reader.readValue(attributeArrayJsonString);
        assertNotNull(attributeArray);

        attributeArray.forEach(attributeObject -> log.info("{}", attributeObject));
    }
}

