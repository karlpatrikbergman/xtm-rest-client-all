package com.infinera.metro.dnam.acceptance.test;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Value
public class XtmDockerRunner {
    private final String xtmDockerImage = "se-artif-prd.infinera.com/tm3k/trunk-hostenv";
    @NonNull private final String xtmDockerVersion;
    @NonNull private final Integer port;
    @NonNull private final String userName;
    @NonNull private final String password;

    public String runDockerContainer() throws DockerException, InterruptedException, DockerCertificateException {

        final String xtmDockerImage = getXtmDockerImage().concat(":").concat(getXtmDockerVersion());
        final DockerClient docker = DefaultDockerClient.fromEnv().build();
        docker.pull(xtmDockerImage);

        final HostConfig hostConfig = HostConfig.builder()
                .privileged(Boolean.TRUE)
                .build();

        final ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .env("DEMO=true", "NOSIM=1")
                .attachStdin(Boolean.TRUE)
                .attachStderr(Boolean.TRUE)
                .tty(Boolean.TRUE)
                .image(xtmDockerImage)
                .build();

        final ContainerCreation creation = docker.createContainer(containerConfig);
        final String id = creation.id();
        docker.startContainer(id);
        return docker.inspectContainer(id).networkSettings().ipAddress();
    }
}
