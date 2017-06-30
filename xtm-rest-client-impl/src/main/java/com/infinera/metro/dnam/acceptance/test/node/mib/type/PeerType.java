package com.infinera.metro.dnam.acceptance.test.node.mib.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PeerType implements MibType {
    PEER("peer");

    private final String value;

    PeerType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static PeerType fromString(String string) {
        return Optional
            .ofNullable(PEER_TYPE_MAP.get(string))
            .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, PeerType> PEER_TYPE_MAP = Stream
        .of(PeerType.values())
        .collect(Collectors.toMap(s -> s.value, Function.identity()));
}

