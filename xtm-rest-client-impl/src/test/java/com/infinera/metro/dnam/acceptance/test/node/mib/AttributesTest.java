package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class AttributesTest {

    @Test
    public void attributeNamesAndValuesToString() {
        final Attributes attributes = Attributes.builder()
            .attributes(Arrays.asList(
                Attribute.builder()
                    .key("topoPeerLocalLabel")
                    .value("1:2:3")
                    .build(),
                Attribute.builder()
                    .key("topoPeerRemoteIpAddress")
                    .value("172.17.0.2")
                    .build(),
                Attribute.builder()
                    .key("topoPeerRemoteSubrack")
                    .value("1")
                    .build(),
                Attribute.builder()
                    .key("topoPeerRemoteSlot")
                    .value("2")
                    .build(),
                Attribute.builder()
                    .key("topoPeerRemotePort")
                    .value("4")
                    .build(),
                Attribute.builder()
                    .key("topoPeerRemoteLabel")
                    .value("1:2:0:4")
                    .build()

            ))
            .build();
        final String expectedParameterString = "topoPeerLocalLabel=1:2:3&topoPeerRemoteIpAddress=172.17.0.2&topoPeerRemoteSubrack=1&topoPeerRemoteSlot=2&topoPeerRemotePort=4&topoPeerRemoteLabel=1:2:0:4";
        final String actualParameterString = attributes.toString();
        assertEquals(expectedParameterString, actualParameterString);
        log.info(attributes.toString());
    }

    @Test
    public void emtpyParameterListToString() {
        Attributes attributes = Attributes.of(Collections.emptyList());
        assertTrue(attributes.toString().length()==0);
    }

    @Test
    public void onlyAttributeNamesToString() {
        final Attributes attributes = Attributes.of(
            Attribute.builder()
                .key("expectedFrequency")
                .build()
        );
        final String expectedString = "expectedFrequency";
        assertEquals(expectedString, attributes.toString());
    }

    @Test
    public void getOnlyKeysToString() {
        final Attributes attributes = Attributes.of(
            Attribute.builder()
                .key("expectedFrequency")
                .value("ch926")
                .build()
        ).onlyKeys();
        final String expectedString = "expectedFrequency";
        assertEquals(expectedString, attributes.toString());
    }

}
