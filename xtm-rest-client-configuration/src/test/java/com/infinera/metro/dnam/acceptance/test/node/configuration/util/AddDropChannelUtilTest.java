package com.infinera.metro.dnam.acceptance.test.node.configuration.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test of utility class "AddDropUtil"
 */

@Slf4j
public class AddDropChannelUtilTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testLastCharIsZero() {
        final String channelString = "9190";
        assertTrue(AddDropChannelUtil.lastCharIsZero(channelString));
    }

    @Test
    public void testLastCharIsNotZero() {
        final String channelString = "9195";
        assertFalse(AddDropChannelUtil.lastCharIsZero(channelString));
    }

    @Test
    public void shouldRemoveLastZero() {
        final String channelString = "9190";
        final String expectedChannelString = "919";
        final String actualChannelString = AddDropChannelUtil.removeLastZero(channelString);
        assertEquals(expectedChannelString, actualChannelString);
    }

    @Test
    public void shouldNotRemoveLastDigit() {
        final String channelString = "9195";
        final String actualChannelString = AddDropChannelUtil.removeLastZero(channelString);
        assertEquals(channelString, actualChannelString);
    }

    @Test
    public void createChannelStringsWithToChannelOutOfRange() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(AddDropChannelUtil.ERROR_MESSAGE_OUT_OF_RANGE);
        AddDropChannelUtil.createAddDropChannelStrings(910.0, 919.0);
    }

    @Test
    public void createChannelStringsWithFromChannelOutOfRange() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(AddDropChannelUtil.ERROR_MESSAGE_OUT_OF_RANGE);
        AddDropChannelUtil.createAddDropChannelStrings(918.0, 960.0);
    }

    @Test
    public void createChannelStringsWithInvalidFromChannel() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(AddDropChannelUtil.ERROR_MESSAGE_VALID_CHANNEL);
        AddDropChannelUtil.createAddDropChannelStrings(918.6, 919.0);
    }


    @Test
    public void createChannelStringsWithInvalidToChannel() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(AddDropChannelUtil.ERROR_MESSAGE_VALID_CHANNEL);
        AddDropChannelUtil.createAddDropChannelStrings(918.5, 919.1);
    }

    @Test
    public void createChannelStrings() {
        List<String> actualChannelStrings = AddDropChannelUtil.createAddDropChannelStrings(918.5, 921.0);
        List<String> expectedChannelStrings = Arrays.asList("ch9185", "ch919", "ch9195", "ch920", "ch9205", "ch921");
        assertEquals(expectedChannelStrings, actualChannelStrings);
    }
}