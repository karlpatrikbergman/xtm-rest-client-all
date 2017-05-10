package dnam;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.mib.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class LinePortEntryDeserializationTest {
    @Test
    public void test() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(LinePortEntry.class);
        InputStream in = new ResourceInputStream("dnam/line_port_entry.yaml");
        LinePortEntry linePortEntry = reader.readValue(in);
        log.info(ReflectionToStringBuilder.toString(linePortEntry, ToStringStyle.MULTI_LINE_STYLE));
    }
}
