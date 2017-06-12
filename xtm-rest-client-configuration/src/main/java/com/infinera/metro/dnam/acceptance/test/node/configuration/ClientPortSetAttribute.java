package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.ConfigurationList;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

@Value
public class ClientPortSetAttribute implements PortAttribute {
    private ConfigurationList configurationList;

    public void applyTo(Node node, MibEntry clientPortEntry) {
        Preconditions.checkArgument(clientPortEntry instanceof ClientPortEntry, "Expected argument of type ClientPortEntry");
        node.setClientPortAttributes((ClientPortEntry) clientPortEntry, configurationList);
    }

    public static ClientPortSetAttribute of(ConfigurationList configurationList) {
        return new ClientPortSetAttribute(configurationList);
    }
}
