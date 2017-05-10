package dnam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class NodeAccessDataTest {

    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream in = new ResourceInputStream("dnam/node_access_data.yaml");
        NodeAccessData nodeAccessData = mapper.readValue(in, NodeAccessData.class);
        log.info(ReflectionToStringBuilder.toString(nodeAccessData, ToStringStyle.MULTI_LINE_STYLE));
    }

    @Test
    public void test2() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(NodeAccessData.class);
        InputStream in = new ResourceInputStream("dnam/node_access_data.yaml");
        NodeAccessData nodeAccessData = reader.readValue(in);
        log.info(ReflectionToStringBuilder.toString(nodeAccessData, ToStringStyle.MULTI_LINE_STYLE));
    }
}

