package com.infinera.metro.dnam.acceptance.test.node.mib.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum InternalConnectionType implements MibType {
    INT("int");

    private final String value;

    InternalConnectionType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static InternalConnectionType fromString(String string) {
        return Optional
            .ofNullable(INTERNAL_CONNECTION_TYPE_MAP.get(string))
            .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, InternalConnectionType> INTERNAL_CONNECTION_TYPE_MAP = Stream
        .of(InternalConnectionType.values())
        .collect(Collectors.toMap(s -> s.value, Function.identity()));
}

