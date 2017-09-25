package com.infinera.metro.dnam.acceptance.test.node;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.junit.Assert.assertNotEquals;

@Slf4j
public class NodeConnectionTest {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/integrationTest/resources/docker-compose.yml")
            .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
            .build();
        private final String nodeIpAddress = "172.45.0.101";

    private final NodeConnection nodeConnection = new NodeConnection(
            NodeAccessData.builder()
                .ipAddress(nodeIpAddress)
                .port(80)
                .userName("root")
                .password("root")
                .build(),
            REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    @Test
    public void loginAndSetSessionId() {
        nodeConnection.loginAndSetSessionId();
        assertNotEquals(0, nodeConnection.getSessionId());
    }
}
