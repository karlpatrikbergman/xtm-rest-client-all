package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

@Value
public class ClientPortSetAttributes implements MibEntryAttributes {
    private Configurations configurations;

    public void applyTo(Node node, MibEntry clientPortEntry) {
        Preconditions.checkArgument(clientPortEntry instanceof ClientPortEntry, "Expected argument of type ClientPortEntry");
        node.setClientPortAttributes((ClientPortEntry) clientPortEntry, configurations);
    }

    public static ClientPortSetAttributes of(Configurations configurations) {
        return new ClientPortSetAttributes(configurations);
    }
}
