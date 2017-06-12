package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;

import java.io.IOException;

class BoardTypeDeserializer extends JsonDeserializer<BoardType> {

    @Override
    public BoardType deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return BoardType.fromString(jsonParser.getValueAsString());
    }
}

