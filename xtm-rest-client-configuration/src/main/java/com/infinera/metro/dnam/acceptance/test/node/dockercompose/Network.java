package com.infinera.metro.dnam.acceptance.test.node.dockercompose;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Network {
    private String driver;
    private Map<String, List<Map>> ipam;
}
