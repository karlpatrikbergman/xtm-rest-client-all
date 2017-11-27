package com.infinera.metro.dnam.acceptance.test.docker;

import com.palantir.docker.compose.DockerComposeRule;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


//TODO: Merge with DockerUtil in dnam-functional-acceptance-tests
public enum DockerUtil2 {
    DOCKER_UTIL;

    public String getContainerIpAddress(DockerComposeRule dockerComposeRule, String nodeName) throws IOException {
        final InputStream inputStream = dockerComposeRule.dockerExecutable().execute("inspect", "-f", "'{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'", nodeName).getInputStream();
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name())
            .replaceAll("\n","")
            .replaceAll("\'","");
    }
}