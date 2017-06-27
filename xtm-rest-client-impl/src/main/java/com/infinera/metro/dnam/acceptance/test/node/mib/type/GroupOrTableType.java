package com.infinera.metro.dnam.acceptance.test.node.mib.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GroupOrTableType implements MibType {
    BOARD("board"),
    IF("if"),
    PEER("peer"),
    ADD_DROP_IF("addDropIf");

    private final String value;

    GroupOrTableType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static GroupOrTableType fromString(String string) {
        return Optional
                .ofNullable(GROUP_OR_TABLE_TYPE_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }

    private static final Map<String, GroupOrTableType> GROUP_OR_TABLE_TYPE_MAP = Stream
            .of(GroupOrTableType.values())
            .collect(Collectors.toMap(s -> s.value, Function.identity()));
}
