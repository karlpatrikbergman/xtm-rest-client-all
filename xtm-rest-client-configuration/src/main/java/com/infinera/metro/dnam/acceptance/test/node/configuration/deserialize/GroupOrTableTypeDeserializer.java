package com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;

import java.io.IOException;

class GroupOrTableTypeDeserializer extends JsonDeserializer<GroupOrTableType> {

    @Override
    public GroupOrTableType deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return GroupOrTableType.fromString(jsonParser.getValueAsString());
    }
}

