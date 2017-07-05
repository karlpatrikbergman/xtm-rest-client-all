package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

@Builder
@Value
public class Foo {
    @Singular private final Map<String, String> bars;
}
