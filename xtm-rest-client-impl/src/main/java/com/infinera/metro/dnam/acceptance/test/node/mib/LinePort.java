package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LinePort {
    WDM("wdm");

    private final String name;

    LinePort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static LinePort fromString(String string) {
        return Optional
                .ofNullable(LINE_PORT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static Map<String, LinePort> LINE_PORT_MAP = Stream
            .of(LinePort.values())
            .collect(Collectors.toMap(s -> s.name, Function.identity()));
}

