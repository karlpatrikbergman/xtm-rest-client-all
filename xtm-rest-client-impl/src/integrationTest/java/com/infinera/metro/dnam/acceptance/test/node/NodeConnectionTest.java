package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.docker.DockerUtil2;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.junit.Assert.assertNotEquals;

@Slf4j
public class NodeConnectionTest {

    @ClassRule
    public static DockerComposeRule dockerComposeRule = DockerComposeRule.builder()
        .file("src/integrationTest/resources/node-connection-test/docker-compose.yml")
        .waitingForService("nodeA", HealthChecks.toHaveAllPortsOpen())
        .build();

    @Test
    public void loginAndSetSessionId() throws IOException {
        DockerUtil2 dockerUtil2 = DockerUtil2.DOCKER_UTIL;
        final String nodeIpAddress = dockerUtil2.getContainerIpAddress(dockerComposeRule, "nodeA");
        final NodeConnection nodeConnection = new NodeConnection(
            NodeAccessData.builder()
                .ipAddress(nodeIpAddress)
                .port(80)
                .userName("root")
                .password("root")
                .build(),
            REST_TEMPLATE_FACTORY.createRestTemplate());
        nodeConnection.loginAndSetSessionId();
        assertNotEquals(0, nodeConnection.getSessionId());
    }
}
