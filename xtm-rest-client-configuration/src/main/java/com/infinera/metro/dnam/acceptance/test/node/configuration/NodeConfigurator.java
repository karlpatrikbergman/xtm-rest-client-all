package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.XtmDockerRunner2;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.util.ThreadSleepWrapper;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Value
public class NodeConfigurator {
    @NonNull XtmDockerRunner2 xtmDockerRunner;
    @NonNull String xtmVersion;
    @NonNull String containerName;
    @NonNull NodeConfiguration.NodeConfigurationBuilder nodeConfigurationBuilder;
    @NonFinal @Builder.Default private boolean attemptedToRunAndConfigureNode = false;

    public NodeConfiguration runAndConfigureNode() throws RuntimeException {
        if (attemptedToRunAndConfigureNode) {
            throw new RuntimeException("Already tried to run and configure node with container name " + containerName);
        }

        final String nodeIpAddress = xtmDockerRunner.runDockerContainer(xtmVersion, containerName);
        ThreadSleepWrapper.sleep(5); //Unnecessary?
        final NodeConfiguration nodeConfiguration = nodeConfigurationBuilder
                .node(NodeImpl.create(NodeAccessData.createDefault(nodeIpAddress)))
                .build();
        nodeConfiguration.apply();
        attemptedToRunAndConfigureNode = true;
        return nodeConfiguration;
    }
}
