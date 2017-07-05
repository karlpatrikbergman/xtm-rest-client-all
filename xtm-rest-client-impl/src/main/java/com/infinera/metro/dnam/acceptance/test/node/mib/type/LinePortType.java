package com.infinera.metro.dnam.acceptance.test.node.mib.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LinePortType implements MibType {
    WDM("wdm"), LINE("line");

    private final String value;

    LinePortType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static LinePortType fromString(String string) {
        return Optional
                .ofNullable(LINE_PORT_TYPE_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, LinePortType> LINE_PORT_TYPE_MAP = Stream
            .of(LinePortType.values())
            .collect(Collectors.toMap(s -> s.value, Function.identity()));
}

