package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.node.mib.Board;

import java.io.IOException;

class BoardDeserializer extends JsonDeserializer<Board> {

    @Override
    public Board deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return Board.fromString(jsonParser.getValueAsString());
    }
}

