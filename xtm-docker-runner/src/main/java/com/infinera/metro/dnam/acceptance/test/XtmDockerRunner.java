package com.infinera.metro.dnam.acceptance.test;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
public enum XtmDockerRunner {
    INSTANCE;
    private final String xtmDockerImage = "se-artif-prd.infinera.com/tm3k/trunk-hostenv";
    private final DockerClient dockerClient = DockerClientInstance.INSTANCE.getDockerClient();
    private final ConcurrentSkipListSet<String> containers = new ConcurrentSkipListSet<>();

    public String runDockerContainer(String xtmVersion, String containerName) {
        try {
            final String xtmDockerImageAndVersion = xtmDockerImage.concat(":").concat(xtmVersion);
            dockerClient.pull(xtmDockerImageAndVersion);

            final HostConfig hostConfig = HostConfig.builder()
                    .privileged(Boolean.TRUE)
                    .build();

            final ContainerConfig containerConfig = ContainerConfig.builder()
                    .hostConfig(hostConfig)
                    .env("DEMO=true", "NOSIM=1")
                    .attachStdin(Boolean.TRUE)
                    .attachStderr(Boolean.TRUE)
                    .tty(Boolean.TRUE)
                    .image(xtmDockerImageAndVersion)
                    .build();

            final ContainerCreation creation = dockerClient.createContainer(containerConfig, containerName);
            final String id = creation.id();
            dockerClient.startContainer(id);
            containers.add(id);
            return dockerClient.inspectContainer(id).networkSettings().ipAddress();
        } catch (DockerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopContainers() {
        containers.forEach(containerId -> {
            try {
                dockerClient.stopContainer(containerId, 4);
            } catch (DockerException | InterruptedException e) {
                log.error(e.getMessage());
            }
        });
    }

    public void removeContainers() {
        DockerClient.RemoveContainerParam.removeVolumes();
        containers.forEach(containerId -> {
            try {
                dockerClient.removeContainer(containerId, DockerClient.RemoveContainerParam.removeVolumes());
            } catch (DockerException | InterruptedException e) {
                log.error(e.getMessage());
            }
        });
    }
}
