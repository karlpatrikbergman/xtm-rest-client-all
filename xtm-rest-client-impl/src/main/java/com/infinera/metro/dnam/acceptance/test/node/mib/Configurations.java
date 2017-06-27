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
public class Configurations {
    @NonNull
    @Singular
    private final List<Configuration> configurations;

    @Override
    public String toString() {
        return configurations.stream()
                .map(Configuration::asParameters)
                .collect(Collectors.joining("&"));
    }

    public static Configurations of(Configuration configuration) {
        return Configurations.builder()
                .configuration(configuration)
                .build();
    }

    public static Configurations of(List<Configuration> configurationList) {
        return Configurations.builder()
                .configurations(configurationList)
                .build();
    }

    public static Configurations of(Configuration... configuration) {
        return Configurations.builder()
            .configurations(Arrays.asList(configuration))
            .build();
    }

    public static Configurations of(String key, String... values) {
        final List<Configuration> configurationList = new ArrayList<>();
        Stream.of(values).forEach(
            value -> configurationList.add(
                Configuration.builder()
                    .key(key)
                    .value(value)
                    .build())
        );
        return Configurations.of(configurationList);
    }

    public static Configurations of(String key, List<String> values) {
        return Configurations.of(
            values.stream()
                .map(value -> Configuration.builder()
                .key(key)
                .value(value)
                .build())
                .collect(Collectors.toList())
        );
    }
}
