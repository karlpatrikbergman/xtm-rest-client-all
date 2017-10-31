package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.NonNull;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class Attributes {
    /**
     * This list should ideally be a ConcurrentHashMap or similar, but I have so far failed
     * when serializing/deserializing Maps.
     * TODO: Fix serialization/deserialization problem with Maps, and replace ArrayList with ConcurrentHashMap
     */
    @NonNull
    private final List<Attribute> attributes;

    @java.beans.ConstructorProperties({"attributes"})
    private Attributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public static AttributesBuilder builder() {
        return new AttributesBuilder();
    }

    public Optional<Attribute> getAttributeByKey(String key) {
        return attributes.stream()
            .filter(attribute -> attribute.getKey().equals(key))
            .findFirst();
    }

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

    public static Attributes of(String key, String value) {
        return of(Attribute.builder()
        .key(key)
        .value(value)
        .build());
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

    public static class AttributesBuilder {
        private ArrayList<Attribute> attributes;

        AttributesBuilder() {
        }

        public AttributesBuilder attribute(Attribute attribute) {
            if (this.attributes == null) this.attributes = new ArrayList<>();
            assertNoDuplicateKeys(attribute);
            this.attributes.add(attribute);
            return this;
        }

        public AttributesBuilder attributes(Collection<? extends Attribute> attributes) {
            if (this.attributes == null) this.attributes = new ArrayList<>();
            assertNoDuplicateKeys(attributes);
            this.attributes.addAll(attributes);
            return this;
        }

        public AttributesBuilder clearAttributes() {
            if (this.attributes != null)
                this.attributes.clear();

            return this;
        }

        public Attributes build() {
            List<Attribute> attributes;
            switch (this.attributes == null ? 0 : this.attributes.size()) {
                case 0:
                    attributes = java.util.Collections.emptyList();
                    break;
                case 1:
                    attributes = java.util.Collections.singletonList(this.attributes.get(0));
                    break;
                default:
                    attributes = java.util.Collections.unmodifiableList(new ArrayList<>(this.attributes));
            }

            return new Attributes(attributes);
        }

        public String toString() {
            return "Attributes.AttributesBuilder(attributes=" + this.attributes + ")";
        }

        private void assertNoDuplicateKeys(Collection<? extends Attribute> attributes) {
            attributes.stream()
                .forEach(attribute -> assertNoDuplicateKeys(attribute));
        }

        private void assertNoDuplicateKeys(Attribute attribute) {
            attributes.stream()
                .filter(attribute1 -> attribute1.getKey().equals(attribute.getKey()))
                .findAny()
                .ifPresent(attribute1 -> {throw new IllegalArgumentException("Attribute " + attribute + " was already added to builder");});
        }
    }
}
