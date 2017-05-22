package com.infinera.metro.dnam.acceptance.test.node.configuration2;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.configuration.JacksonYamlUtil;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class NodeEquipment2DeserializationTest {
    @Test
    public void test() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(NodeEquipment2.class);
        InputStream in = new ResourceInputStream("configuration2/node_equipment2.yaml");
        NodeEquipment2 nodeEquipment2 = reader.readValue(in);
        log.info(ReflectionToStringBuilder.toString(nodeEquipment2, ToStringStyle.MULTI_LINE_STYLE));
    }
}
