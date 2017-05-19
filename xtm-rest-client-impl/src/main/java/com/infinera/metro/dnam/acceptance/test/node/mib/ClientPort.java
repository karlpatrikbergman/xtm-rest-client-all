package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Corresponds to first part of "entry" in mib table (without subrack:slot). Find a better name?
public enum ClientPort {
    CLIENT("client");

    private final String name;

    ClientPort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static ClientPort fromString(String string) {
        return Optional
                .ofNullable(CLIENT_PORT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, ClientPort> CLIENT_PORT_MAP = Stream
            .of(ClientPort.values())
            .collect(Collectors.toMap(s -> s.name, Function.identity()));
}
