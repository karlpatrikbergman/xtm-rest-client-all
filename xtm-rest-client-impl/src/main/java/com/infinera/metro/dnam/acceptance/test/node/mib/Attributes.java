package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//TODO: Force type check?
//Can only be a list with attributes that are set with the same operation (set.json or configure.json)
//At least I think so. How add type safety? Is it necessary?

@Value
@Builder
public class Attributes {
    @NonNull
    @Singular
    private final List<Attribute> attributes;

    @Override
    public String toString() {
        return attributes.stream()
                .map(Attribute::asParameters)
                .collect(Collectors.joining("&"));
    }

    public static Attributes of(Attribute attribute) {
        return Attributes.builder()
                .attribute(attribute)
                .build();
    }

    public static Attributes of(List<Attribute> attributeList) {
        return Attributes.builder()
                .attributes(attributeList)
                .build();
    }

    public static Attributes of(Attribute... attribute) {
        return Attributes.builder()
            .attributes(Arrays.asList(attribute))
            .build();
    }

    public static Attributes of(String key, String... values) {
        final List<Attribute> attributeList = new ArrayList<>();
        Stream.of(values).forEach(
            value -> attributeList.add(
                Attribute.builder()
                    .key(key)
                    .value(value)
                    .build())
        );
        return Attributes.of(attributeList);
    }

    public static Attributes of(String key, List<String> values) {
        return Attributes.of(
            values.stream()
                .map(value -> Attribute.builder()
                .key(key)
                .value(value)
                .build())
                .collect(Collectors.toList())
        );
    }

    /**
     * When retrieving attributes we only want the keys. Using this method we can re-use attributes of previous set
     * operation. OBS! Cannot be used when in case of configuring and then retrieving. When configuring attribute key
     * will not be the same as attribute name.
     */
    public Attributes onlyKeys() {
        AttributesBuilder attributesOnlyKeysBuilder = Attributes.builder();
        attributes.forEach(attribute -> attributesOnlyKeysBuilder.attribute(
            Attribute.builder()
                .key(attribute.getKey())
                .build()));
        return attributesOnlyKeysBuilder.build();
    }
}
