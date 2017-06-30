package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

//TODO: Can attributes for line ports be set using configure.json? If so add methods to Node interface in the same manner
//as for client port configuration
@Value
public class LinePortSetAttributes implements MibEntryAttributes {
    private Configurations configurations;

    public void applyTo(Node node, MibEntry linePortEntry) {
        Preconditions.checkArgument(linePortEntry instanceof LinePortEntry, "Expected argument of type LinePortEntry");
        node.setLinePortAttributes((LinePortEntry) linePortEntry, configurations);
    }

    public static LinePortSetAttributes of(Configurations configurations) {
        return new LinePortSetAttributes(configurations);
    }

    public static LinePortSetAttributes of(Configuration configuration) {
        return LinePortSetAttributes.of(Configurations.of(configuration));
    }
}
