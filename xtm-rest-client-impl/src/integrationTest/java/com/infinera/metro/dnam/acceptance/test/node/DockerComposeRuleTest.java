package com.infinera.metro.dnam.acceptance.test.node;

import com.google.common.collect.ImmutableList;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.DockerComposeFiles;
import com.palantir.docker.compose.configuration.ShutdownStrategy;
import com.palantir.docker.compose.connection.DockerMachine;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.junit.ClassRule;

import java.util.List;

public abstract class DockerComposeRuleTest {
    private static final ShutdownStrategy shutdownStrategy;
    private static final DockerMachine dockerMachine;
    private static final ImmutableList.Builder<String> dockerComposeFilesBuilder = new ImmutableList.Builder<>();
    private static final String[] dockerComposeFilesArray;


    static {
        dockerComposeFilesBuilder.add("src/integrationTest/resources/node-impl-smoke-test/docker-compose.yml");

        String shutdownStrategyFromCommandLine = (System.getProperty("shutdownStrategy") != null) ? System.getProperty("shutdownStrategy") : "";
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
            dockerComposeFilesBuilder.add("src/integrationTest/resources/node-impl-smoke-test/docker-compose-macvlan.yml");
            dockerMachine = DockerMachine.remoteMachine()
                .host(System.getProperty("dockerMachineHost"))
                .withTLS(System.getProperty("dockerMachineSshDirectory"))
                .build();
        }

        List<String> dockerComposeFiles = dockerComposeFilesBuilder.build().asList();
        dockerComposeFilesArray = dockerComposeFiles.toArray(new String[dockerComposeFiles.size()]);
    }

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
        .machine(dockerMachine)
        .files(DockerComposeFiles.from(dockerComposeFilesArray))
//        .files(DockerComposeFiles.from("src/integrationTest/resources/node-impl-smoke-test/docker-compose.yml", "src/integrationTest/resources/node-impl-smoke-test/docker-compose-macvlan.yml"))
        .waitingForService("nodeA", HealthChecks.toHaveAllPortsOpen())
        .waitingForService("nodeZ", HealthChecks.toHaveAllPortsOpen())
        .shutdownStrategy(shutdownStrategy)
        .build();
}
