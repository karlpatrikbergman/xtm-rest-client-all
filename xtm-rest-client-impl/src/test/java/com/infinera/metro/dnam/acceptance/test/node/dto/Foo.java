package com.infinera.metro.dnam.acceptance.test.node.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class Foo {
    private final String rFlags;
    private final String aFlags;

    @JsonCreator
    public static Foo create(@JsonProperty("R") String rFlags, @JsonProperty("A") String aFlags) {
        return new Foo(rFlags, aFlags);
    }

    public Foo(String rFlags, String aFlags) {
        this.rFlags = rFlags;
        this.aFlags = aFlags;
    }

    @JsonProperty("R")
    public String getrFlags() {
        return rFlags;
    }

    @JsonProperty("A")
    public String getaFlags() {
        return aFlags;
    }
}
