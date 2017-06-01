package com.infinera.metro.dnam.acceptance.test.node.configuration;


import lombok.Data;

import java.util.Map;

@Data
public class Person {
    private String name;
    private int age;
    private Person father;
    private Map<String, Person> children;
}