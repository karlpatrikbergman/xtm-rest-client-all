package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LinePortType {
    WDM("wdm");

    private final String name;

    LinePortType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static LinePortType fromString(String string) {
        return Optional
                .ofNullable(LINE_PORT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, LinePortType> LINE_PORT_MAP = Stream
            .of(LinePortType.values())
            .collect(Collectors.toMap(s -> s.name, Function.identity()));
}

