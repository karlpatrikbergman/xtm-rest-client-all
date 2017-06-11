package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.ConfigurationList;
import com.infinera.metro.dnam.acceptance.test.node.mib.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.MibEntry;
import lombok.Value;

//TODO: Can attributes for line ports be set using configure.json? If so add methods to Node interface in the same manner
//as for client port configuration
@Value
public class LinePortConfigAttribute implements PortAttribute {
    private ConfigurationList configurationList;

    public void applyTo(Node node, MibEntry linePortEntry) {
        Preconditions.checkArgument(linePortEntry instanceof LinePortEntry, "Expected argument of type LinePortEntry");
        node.setLinePortAttributes((LinePortEntry) linePortEntry, configurationList);
    }

    public static LinePortConfigAttribute of(ConfigurationList configurationList) {
        return new LinePortConfigAttribute(configurationList);
    }
}
