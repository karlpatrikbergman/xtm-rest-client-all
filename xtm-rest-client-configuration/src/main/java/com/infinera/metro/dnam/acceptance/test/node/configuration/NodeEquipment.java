package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import lombok.Value;

@Value
public class NodeEquipment {
    private final BoardType boardType;
    private final BoardEntry boardEntry;
    private final LinePortEntry linePortEntry;
    private final Configuration linePortConfiguration;
    private final ClientPortEntry clientPortEntry;
    private final Configuration clientPortConfiguration;
    private final MpoIdentifier mpoIdentifier;
}
