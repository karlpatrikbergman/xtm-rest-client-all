package com.infinera.metro.dnam.acceptance.test.node.mib.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BoardType implements MibType {
    TPD10GBE("tpd10gbe"),
    TPMR2500("tpmr2500"),
    ROADM1X2G50("roadm1x2g50"),
    OA2X17DBM("oa2x17dbm"), // //Works for create but not for set.json oa2x17dBm
    OCM2P("ocm2p"),
    TPHEX10GOTN("tphex10gotn"),
    TP100GOTN("tp100gotn"),
    GBE10EMXP10GII("gbe10emxp10gii"),
    MDU40EVENL("mdu40evenl"),
    OIUC50100("oiuc50100"),
    FXP400GOTN("fxp400gotn"),
    AD4EVEN50("ad4even50"),
    OCU2("ocu2");
//    TP10G("tp10g"),
//    OCM2P("oa2x17dBm"),
//    OIUC50100("oiuc50100");


    private final String value;

    BoardType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static BoardType fromString(String string) {
        return Optional
                .ofNullable(BOARD_TYPE_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, BoardType> BOARD_TYPE_MAP = Stream
            .of(BoardType.values())
            .collect(Collectors.toMap(s -> s.value, Function.identity()));
}
