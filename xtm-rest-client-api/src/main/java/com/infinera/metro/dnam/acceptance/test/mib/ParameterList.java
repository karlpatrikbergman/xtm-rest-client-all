package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.Builder;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class ParameterList {
    private final List<Configuration> parameterList;

    @Override
    public String toString() {
        return parameterList.stream()
                .map(Configuration::asParameters)
                .collect(Collectors.joining("&"));
    }

    public static ParameterList of(Configuration configuration) {
        return ParameterList.builder()
                .parameterList(Arrays.asList(configuration))
                .build();
    }

    public static ParameterList of(List<Configuration> configurationList) {
        return ParameterList.builder()
                .parameterList(configurationList)
                .build();
    }

}
