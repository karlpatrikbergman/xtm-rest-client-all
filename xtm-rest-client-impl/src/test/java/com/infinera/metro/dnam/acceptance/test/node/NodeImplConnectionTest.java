package com.infinera.metro.dnam.acceptance.test.node;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.junit.Assert.assertNotEquals;

@Category(IntegrationTest.class)
@Slf4j
public class NodeImplConnectionTest {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
            .build();

    NodeConnection nodeConnection = new NodeConnection(
            NodeAccessData.builder()
                .ipAddress("172.45.0.101")
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
