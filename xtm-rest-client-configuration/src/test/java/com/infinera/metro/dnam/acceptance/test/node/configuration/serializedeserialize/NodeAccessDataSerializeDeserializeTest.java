package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NodeAccessDataSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<NodeAccessData> {

    public NodeAccessDataSerializeDeserializeTest() {
        super(NodeAccessData.class, NodeAccessData.createDefault("172.17.0.2"), "configuration/node_access_data.yaml");
    }

//    @Test
//    public void test() throws IOException {
//        final NodeAccessData nodeAccessData = objectFromFileUtil.getObject("configuration/node_access_data.yaml", NodeAccessData.class);
//        log.info(ReflectionToStringBuilder.toString(nodeAccessData, ToStringStyle.MULTI_LINE_STYLE));
//    }
}

