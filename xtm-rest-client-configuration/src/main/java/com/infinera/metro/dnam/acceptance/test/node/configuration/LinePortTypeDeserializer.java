package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;

import java.io.IOException;

class LinePortTypeDeserializer extends JsonDeserializer<LinePortType> {

    @Override
    public LinePortType deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return LinePortType.fromString(jsonParser.getValueAsString());
    }
}

