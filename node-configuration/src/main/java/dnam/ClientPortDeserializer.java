package dnam;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.mib.ClientPort;

import java.io.IOException;

public class ClientPortDeserializer extends JsonDeserializer<ClientPort> {

    @Override
    public ClientPort deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return ClientPort.fromString(jsonParser.getValueAsString());
    }
}

