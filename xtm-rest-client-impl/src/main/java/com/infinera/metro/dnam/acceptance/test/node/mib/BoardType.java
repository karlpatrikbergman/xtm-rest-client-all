package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BoardType {
    TP10G("tp10g"),
    TPD10GBE("tpd10gbe");

    private final String name;

    BoardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static BoardType fromString(String string) {
        return Optional
                .ofNullable(BOARD_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, BoardType> BOARD_MAP = Stream
            .of(BoardType.values())
            .collect(Collectors.toMap(s -> s.name, Function.identity()));
}
