package com.infinera.metro.dnam.acceptance.test.node.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.AttributeObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AnswerObjectDeserializer extends JsonDeserializer<AnswerObject> {

    @Override
    public AnswerObject deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);

        return AnswerObject.builder()
                .rFlags(node.path("R").asText())
                .aFlags(node.path("A").asText())
                .queryID(node.path("I").asInt())
                .success(node.path("S").asBoolean())
                .error(node.path("E").asText())
                .module(node.path("M").asText())
                .groupOrTable(node.path("G").asText())
                .entry(node.path("N").asText())
                .operation(node.path("O").asText())
                .queryString(node.path("Q").asText())
                .attributeObjectList(attributeArrayToAttributeOjbectList(node.path("T")))
                .build();
    }

    private List<AttributeObject> attributeArrayToAttributeOjbectList(JsonNode node) throws IOException {
        if(node.isMissingNode()) {
            return Collections.emptyList();
        }
        ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AttributeObject>>(){});
        return reader.readValue(node);
    }
}

