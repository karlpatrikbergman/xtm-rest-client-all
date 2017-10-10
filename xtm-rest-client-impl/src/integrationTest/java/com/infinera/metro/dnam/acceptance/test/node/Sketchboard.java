package com.infinera.metro.dnam.acceptance.test.node;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Sketchboard {

    @Test
    public void test() {
        ImmutableList.Builder<String> dockerComposeFilesBuilder = new ImmutableList.Builder<>();
        dockerComposeFilesBuilder.add("\"src/integrationTest/resources/default-xtm-nodes-setup/docker-compose.yml\"");
        dockerComposeFilesBuilder.add("\"src/integrationTest/resources/default-xtm-nodes-setup/dockerComposeRule-compose-macvlan.yml\"");
        String dockerComposeFiles = String.join(",", dockerComposeFilesBuilder.build().asList());

        log.info("dockerComposeFiles {}", dockerComposeFiles);
    }
}
