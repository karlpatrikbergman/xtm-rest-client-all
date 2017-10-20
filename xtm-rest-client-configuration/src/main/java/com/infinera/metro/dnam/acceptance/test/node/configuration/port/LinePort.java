package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;

//TODO: Delete class?

public interface LinePort {
    LinePortEntry getLinePortEntry();
    Attribute getLinePortConfiguration();

}
