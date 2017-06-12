package com.infinera.metro.dnam.acceptance.test.node.mib.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ModuleType {
    EQ("eq"),
    WDM("wdm"),
    CLIENT("client"),
    TOPO("topo"),
    ROADM("roadm"),
    OA("oa");

    private final String value;

    ModuleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ModuleType fromString(String string) {
        return Optional
                .ofNullable(MODULE_TYPE_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, ModuleType> MODULE_TYPE_MAP = Stream
            .of(ModuleType.values())
            .collect(Collectors.toMap(s -> s.value, Function.identity()));
}
