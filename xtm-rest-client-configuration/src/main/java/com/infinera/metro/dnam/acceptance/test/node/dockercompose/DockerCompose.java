package com.infinera.metro.dnam.acceptance.test.node.dockercompose;

import lombok.Data;

import java.util.Map;

@Data
public final class DockerCompose {
    private String version;
    private Map<String, Service> services;
    private Map<String, Network> networks;
}
