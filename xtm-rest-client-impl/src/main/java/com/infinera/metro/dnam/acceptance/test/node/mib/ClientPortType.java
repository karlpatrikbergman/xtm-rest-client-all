package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO: Can we delegate common behaviour to get code reuse?
//TODO: Implement PortType

public enum ClientPortType {
    CLIENT("client"),
    MRT("mrt"),
    ADD_DROP("addDrop");

    private final String value;

    ClientPortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ClientPortType fromString(String string) {
        return Optional
                .ofNullable(CLIENT_PORT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, ClientPortType> CLIENT_PORT_MAP = Stream
            .of(ClientPortType.values())
            .collect(Collectors.toMap(s -> s.value, Function.identity()));
}
