package com.infinera.metro.dnam.acceptance.test;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum  DockerClientInstance {
    INSTANCE;

    private DockerClient dockerClient;

    DockerClientInstance() {
        try {
            this.dockerClient = DefaultDockerClient.fromEnv().build();
        } catch (DockerCertificateException e) {
            logError(e.getMessage());
        }
    }

    public DockerClient getDockerClient() {
        return dockerClient;
    }

    /** Workaround for compile error
     * "It is illegal to access static member 'log' from enum constructor or instance initializer"
     */
    private void logError(String message) {
        log.error(message);
    }
}
