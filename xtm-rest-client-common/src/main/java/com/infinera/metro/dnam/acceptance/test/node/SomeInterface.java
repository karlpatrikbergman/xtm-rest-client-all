package com.infinera.metro.dnam.acceptance.test.node;

public interface SomeInterface {
    static void someMethod() {
        System.out.println(SomeInterface.class.getSimpleName() + ".someMethod()");
    }
}
