package com.infinera.metro.dnam.acceptance.test.node.configuration;

import org.junit.Test;

public class FooTest {

    @Test
    public void mapBuilderTest() {
        Foo foo = Foo.builder()
            .bar("SomeKey", "Some value")
            .build();
    }
}
