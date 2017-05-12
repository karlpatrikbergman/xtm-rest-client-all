package com.infinera.metro.dnam.acceptance.test.node.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.infinera.metro.dnam.acceptance.test.node.dto.AttributeObject;

import java.io.IOException;

/*
Attribute object / _AFLAGS_
---------------------------
As specified in ppt document:
    Members:
    A               alias           string
    V               value           string
    N               name            string
    D               longName        string
    H               helpText        string
    P               propertyMask    number
    U               unit            string
    I               minValue        number
    M               maxValue        number
    J               majorType       number
    O               minorType       number
    S               setMessage      string
    E               enumRange [ ]   array
    X               include all of the above

*/
class AttributeObjectDeserializer extends JsonDeserializer<AttributeObject> {

    @Override
    public AttributeObject deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        return AttributeObject.builder()
                .alias(node.path("A").asText())
                .value(node.path("V").asText())
                .name(node.path("N").asText())
                .longName(node.path("D").asText())
                .helpText(node.path("H").asText())
                .propertyMask(node.path("P").asInt())
                .minValue(node.path("I").asInt())
                .maxValue(node.path("M").asInt())
                .majorType(node.path("J").asInt())
                .minorType(node.path("O").asInt())
                .build();
    }
}
