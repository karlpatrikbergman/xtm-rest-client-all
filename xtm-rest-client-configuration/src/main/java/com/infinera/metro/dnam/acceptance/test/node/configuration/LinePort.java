package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;

public interface LinePort {
    LinePortEntry getLinePortEntry();
    Configuration getLinePortConfiguration();

}
