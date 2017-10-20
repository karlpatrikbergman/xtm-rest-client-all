package com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;

import java.io.IOException;

class ModuleTypeDeserializer extends JsonDeserializer<ModuleType> {

    @Override
    public ModuleType deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return ModuleType.fromString(jsonParser.getValueAsString());
    }
}