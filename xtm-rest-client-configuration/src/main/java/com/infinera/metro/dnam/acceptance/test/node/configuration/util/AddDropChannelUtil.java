package com.infinera.metro.dnam.acceptance.test.node.configuration.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TODO: Change (or add constructor) constructor arguments to "ch918" and the like?
@Slf4j
public class AddDropChannelUtil {
    private static final Double MIN_VALUE = 918.0;
    private static final Double MAX_VALUE = 958.0;
    static final String ERROR_MESSAGE_OUT_OF_RANGE = "Channel number must be between "+ MIN_VALUE +" and " + MAX_VALUE;
    static final String ERROR_MESSAGE_VALID_CHANNEL = "Channel number must be valid, either a whole number or with decimal .5";

    public static List<String> createAddDropChannelStrings(Double fromChannelNumber, Double toChannelNumber) {
        Preconditions.checkArgument(fromChannelNumber >= MIN_VALUE && toChannelNumber <= MAX_VALUE, ERROR_MESSAGE_OUT_OF_RANGE);
        Preconditions.checkArgument(isValidEvenChannelNumber(fromChannelNumber) || isValidOddChannelNumber(fromChannelNumber), ERROR_MESSAGE_VALID_CHANNEL);
        Preconditions.checkArgument(isValidEvenChannelNumber(toChannelNumber) || isValidOddChannelNumber(toChannelNumber), ERROR_MESSAGE_VALID_CHANNEL);

        return IntStream.rangeClosed(fromChannelNumber.intValue(), toChannelNumber.intValue())
            .asDoubleStream()
            .mapToObj(value -> Arrays.asList(value, value+0.5))
            .flatMap(Collection::stream)
            .filter(value -> value >= fromChannelNumber)
            .filter(value -> value <= toChannelNumber)
            .map(value -> value*10)
            .map(Double::intValue)
            .map(value -> Integer.toString(value))
            .map(AddDropChannelUtil::removeLastZero)
            .map(value -> "ch" + value)
            .collect(Collectors.toList());
    }

    static boolean isValidEvenChannelNumber(double channelNumber) {
        return  channelNumber % 1 == 0;
    }

    static boolean isValidOddChannelNumber(double channelNumber) {
        return channelNumber - Math.floor(channelNumber) == 0.5;
    }

    //TODO: Test more thoroughly
    static String removeLastZero(String string) {
        if (lastCharIsZero(string)) {
            return string.substring(0, string.length()-1);
        }
        return string;
    }

    static boolean lastCharIsZero(String string) {
        return string.charAt(string.length()-1) == '0';
    }
}
