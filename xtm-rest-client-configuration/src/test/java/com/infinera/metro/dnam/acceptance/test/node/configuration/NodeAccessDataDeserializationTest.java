package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class NodeAccessDataDeserializationTest {

    public static final String PATH = "configuration/node_access_data.yaml";

    @Test
    public void test2() throws IOException {
        final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();
        final NodeAccessData nodeAccessData = objectFromFileUtil.getObject(PATH, NodeAccessData.class);
        log.info(ReflectionToStringBuilder.toString(nodeAccessData, ToStringStyle.MULTI_LINE_STYLE));
    }
}

