package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BoardType {
    TPD10GBE("tpd10gbe"),
    TPMR2500("tpmr2500"),
    ROADM1X2G50("roadm1x2G50"),
    OA2X17DBM("oa2x17dBm");
//    TP10G("tp10g"),
//    OCM2P("oa2x17dBm"),
//    AD4EVEN50("ad4Even50"),
//    OIUC50100("oiuc50100");

    private final String value;

    BoardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static BoardType fromString(String string) {
        return Optional
                .ofNullable(BOARD_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, BoardType> BOARD_MAP = Stream
            .of(BoardType.values())
            .collect(Collectors.toMap(s -> s.value, Function.identity()));
}
