package com.infinera.metro.dnam.acceptance.test.node.mib.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OcmPortType implements MibType {
    OCM("ocm");

    private final String value;

    OcmPortType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OcmPortType fromString(String string) {
        return Optional
            .ofNullable(PORT_TYPE_MAP.get(string))
            .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, OcmPortType> PORT_TYPE_MAP = Stream
        .of(OcmPortType.values())
        .collect(Collectors.toMap(s -> s.value, Function.identity()));
}
