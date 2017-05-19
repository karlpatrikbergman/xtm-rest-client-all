package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.node.mib.ClientPort;

import java.io.IOException;

class ClientPortDeserializer extends JsonDeserializer<ClientPort> {

    @Override
    public ClientPort deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return ClientPort.fromString(jsonParser.getValueAsString());
    }
}

