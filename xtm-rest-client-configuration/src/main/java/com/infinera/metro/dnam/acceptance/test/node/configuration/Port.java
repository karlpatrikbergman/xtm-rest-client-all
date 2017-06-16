package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * NOTE:
 * Field portEntryAttributes could be two lists instead of one, one for attributes that uses "set.json" and one that
 * uses "configure.json".
 */

@Value
@Builder
public class Port {
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;
    @NonNull @Singular private final List<MibEntryAttributes> portEntryAttributes;

}
