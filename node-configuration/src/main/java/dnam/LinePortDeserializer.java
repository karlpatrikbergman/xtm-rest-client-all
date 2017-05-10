package dnam;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infinera.metro.dnam.acceptance.test.mib.Board;
import com.infinera.metro.dnam.acceptance.test.mib.LinePort;

import java.io.IOException;

public class LinePortDeserializer extends JsonDeserializer<LinePort> {

    @Override
    public LinePort deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return LinePort.fromString(jsonParser.getValueAsString());
    }
}

