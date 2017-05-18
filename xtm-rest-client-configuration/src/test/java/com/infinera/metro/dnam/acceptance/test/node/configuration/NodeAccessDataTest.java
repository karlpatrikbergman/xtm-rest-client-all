package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectReader;
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
    public void test2() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(NodeAccessData.class);
        InputStream in = new ResourceInputStream("configuration/node_access_data.yaml");
        NodeAccessData nodeAccessData = reader.readValue(in);
        log.info(ReflectionToStringBuilder.toString(nodeAccessData, ToStringStyle.MULTI_LINE_STYLE));
    }
}

