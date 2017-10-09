package com.infinera.metro.dnam.acceptance.test.node;

import com.palantir.docker.compose.DockerComposeRule;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

enum DockerUtil {
    DOCKER_UTIL;

    String getContainerIpAddress(DockerComposeRule dockerComposeRule, String nodeName) throws IOException {
        final InputStream inputStream = dockerComposeRule.dockerExecutable().execute("inspect", "-f", "'{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'", nodeName).getInputStream();
        final String ipAddress = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name())
            .replaceAll("\n","")
            .replaceAll("\'","");
        return ipAddress;
    }
}