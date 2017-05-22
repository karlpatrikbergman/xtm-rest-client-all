package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.node.mib.ClientPortType;

import java.io.IOException;

class ClientPortTypeDeserializer extends JsonDeserializer<ClientPortType> {

    @Override
    public ClientPortType deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return ClientPortType.fromString(jsonParser.getValueAsString());
    }
}

