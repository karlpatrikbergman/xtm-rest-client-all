package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.Builder;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//TODO: Can only be a list with attributes that are set with the same operation (set.json or configure.json)
//At least I think so...

@Value
@Builder
public class ConfigurationList {
    private final List<Configuration> parameterList;

    @Override
    public String toString() {
        return parameterList.stream()
                .map(Configuration::asParameters)
                .collect(Collectors.joining("&"));
    }

    public static ConfigurationList of(Configuration configuration) {
        return ConfigurationList.builder()
                .parameterList(Arrays.asList(configuration))
                .build();
    }

    public static ConfigurationList of(List<Configuration> configurationList) {
        return ConfigurationList.builder()
                .parameterList(configurationList)
                .build();
    }
}
