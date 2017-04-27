package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.deserializer.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.experimental.categories.Category;

import java.util.List;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.junit.Assert.assertNotEquals;

@Category(IntegrationTest.class)
@Slf4j
public class NodeConnectionTest {

//    @ClassRule
//    public static DockerComposeRule docker = DockerComposeRule.builder()
//            .file("src/test/resources/docker-compose.yml")
//            .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
//            .build();

    private final String nodeIpAddress ="172.17.0.2";
    //    private final String nodeIpAddress = "172.45.0.101";

    ObjectMapper mapper = new ObjectMapper();

    NodeConnection nodeConnection = new NodeConnection(
            NodeAccessData.builder()
                .ipAddress(nodeIpAddress)
                .port(80)
                .userName("root")
                .password("root")
                .build(),
            REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    private final ObjectReader objectReader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>(){});

//    @Test
    public void loginAndSetSessionId() {
        nodeConnection.loginAndSetSessionId();
        assertNotEquals(0, nodeConnection.getSessionId());
    }


}
