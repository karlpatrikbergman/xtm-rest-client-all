package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@Slf4j
public class ParameterListTest {
    ParameterList parameterList = ParameterList.builder()
            .parameterList(Arrays.asList(
                Configuration.builder()
                        .key("topoPeerLocalLabel")
                        .value("1:2:3")
                        .build(),
                Configuration.builder()
                        .key("topoPeerRemoteIpAddress")
                        .value("172.17.0.2")
                        .build(),
                Configuration.builder()
                        .key("topoPeerRemoteSubrack")
                        .value("1")
                        .build(),
                Configuration.builder()
                        .key("topoPeerRemoteSlot")
                        .value("2")
                        .build(),
                Configuration.builder()
                        .key("topoPeerRemotePort")
                        .value("4")
                        .build(),
                Configuration.builder()
                        .key("topoPeerRemoteLabel")
                        .value("1:2:0:4")
                        .build()

                ))
            .build();

    @Test
    public void parameterListToString() {
        final String expectedParameterString = "topoPeerLocalLabel=1:2:3&topoPeerRemoteIpAddress=172.17.0.2&topoPeerRemoteSubrack=1&topoPeerRemoteSlot=2&topoPeerRemotePort=4&topoPeerRemoteLabel=1:2:0:4";
        final String acctualParameterString = parameterList.toString();
        assertEquals(expectedParameterString, acctualParameterString);
        log.info(parameterList.toString());
    }

}
