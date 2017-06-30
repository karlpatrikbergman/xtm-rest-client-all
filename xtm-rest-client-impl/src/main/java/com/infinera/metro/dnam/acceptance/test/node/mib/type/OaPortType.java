package com.infinera.metro.dnam.acceptance.test.node.mib.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OaPortType implements MibType {
    OA("oa");

    private final String value;

    OaPortType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OaPortType fromString(String string) {
        return Optional
            .ofNullable(OA_PORT_TYPE_MAP.get(string))
            .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, OaPortType> OA_PORT_TYPE_MAP = Stream
        .of(OaPortType.values())
        .collect(Collectors.toMap(s -> s.value, Function.identity()));
}
