package com.infinera.metro.dnam.acceptance.test.node;

import com.google.common.collect.ImmutableList;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.DockerComposeFiles;
import com.palantir.docker.compose.configuration.ShutdownStrategy;
import com.palantir.docker.compose.connection.DockerMachine;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;

import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class DockerComposeRuleTest {
    private static final ShutdownStrategy shutdownStrategy;
    private static final DockerMachine dockerMachine;
    private static final ImmutableList.Builder<String> dockerComposeFilesBuilder = new ImmutableList.Builder<>();
    private static final String[] dockerComposeFilesArray;


    static {
        dockerComposeFilesBuilder.add("src/integrationTest/resources/node-impl-smoke-test/docker-compose.yml");
        final String shutdownStrategyFromCommandLine = (System.getProperty("shutdownStrategy") != null) ? System.getProperty("shutdownStrategy") : "";

        log.info("System.getProperty(\"shutdownStrategy\"): {}", System.getProperty("shutdownStrategy"));
        log.info("System.getProperty(\"dockerMachineHost\"): {}", System.getProperty("dockerMachineHost"));
        log.info("System.getProperty(\"dockerMachineSshDirectory\"): {}", System.getProperty("dockerMachineSshDirectory"));

        switch (shutdownStrategyFromCommandLine) {
            case "SKIP":
                shutdownStrategy = ShutdownStrategy.SKIP;
                break;
            case "GRACEFUL":
                shutdownStrategy = ShutdownStrategy.GRACEFUL;
                break;
            case "KILL_DOWN":
                shutdownStrategy = ShutdownStrategy.GRACEFUL;
                break;
            default:
                shutdownStrategy = ShutdownStrategy.GRACEFUL;
        }

        if(System.getProperty("dockerMachineHost") == null || System.getProperty("dockerMachineSshDirectory") == null) {
            dockerMachine = DockerMachine.localMachine().build();
        } else {
            dockerComposeFilesBuilder.add("src/integrationTest/resources/node-impl-smoke-test/dockerComposeRule-compose-macvlan.yml");
            dockerMachine = DockerMachine.remoteMachine()
                .host(System.getProperty("dockerMachineHost"))
                .withTLS(System.getProperty("dockerMachineSshDirectory"))
                .build();
        }

        List<String> dockerComposeFiles = dockerComposeFilesBuilder.build().asList();
        dockerComposeFilesArray = dockerComposeFiles.toArray(new String[dockerComposeFiles.size()]);

        Arrays.stream(dockerComposeFilesArray).forEach(file -> log.info("dockerComposeRule-compose file: {}", file));
    }

    @ClassRule
    public static DockerComposeRule dockerComposeRule = DockerComposeRule.builder()
        .machine(dockerMachine)
        .files(DockerComposeFiles.from(dockerComposeFilesArray))
        .waitingForService("nodeA", HealthChecks.toHaveAllPortsOpen())
        .waitingForService("nodeZ", HealthChecks.toHaveAllPortsOpen())
        .shutdownStrategy(shutdownStrategy)
        .build();
}
