package com.infinera.metro.dnam.acceptance.test.mib;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//TODO: Change all enums to end with "Type"
//Corresponds to first part of "entry" in mib table (without subrack:slot). Find a better name?
public enum Board {
    TP10G("tp10g"),
    TPD10GBE("tpd10gbe");

    private final String name;

    Board(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static Board fromString(String string) {
        return Optional
                .ofNullable(BOARD_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static Map<String, Board> BOARD_MAP = Stream
            .of(Board.values())
            .collect(Collectors.toMap(s -> s.name, Function.identity()));
}
