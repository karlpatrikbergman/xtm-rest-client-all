package dnam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.infinera.metro.dnam.acceptance.test.mib.Board;
import com.infinera.metro.dnam.acceptance.test.mib.ClientPort;
import com.infinera.metro.dnam.acceptance.test.mib.LinePort;

public enum JacksonYamlUtil {
    INSTANCE;

    final ObjectMapper mapper;

    JacksonYamlUtil() {
        mapper = new ObjectMapper(new YAMLFactory());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Board.class, new BoardDeserializer());
        module.addDeserializer(LinePort.class, new LinePortDeserializer());
        module.addDeserializer(ClientPort.class, new ClientPortDeserializer());
        mapper.registerModule(module);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //Normally, default ObjectMapper cannot deserialize this message into a CarInfo object.
        //With following configuration, it's possible:
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public ObjectReader getReader() {
        return mapper.reader();
    }
}
