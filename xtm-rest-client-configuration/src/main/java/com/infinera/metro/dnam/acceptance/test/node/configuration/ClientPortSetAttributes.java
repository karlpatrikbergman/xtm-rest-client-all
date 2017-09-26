package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

@Value
public class ClientPortSetAttributes implements MibEntryAttributes {
    private Attributes attributes;

    public void applyTo(Node node, MibEntry clientPortEntry) {
        Preconditions.checkArgument(clientPortEntry instanceof ClientPortEntry, "Expected argument of type ClientPortEntry");
        node.setClientPortAttributes((ClientPortEntry) clientPortEntry, attributes);
    }

    public static ClientPortSetAttributes of(Attributes attributes) {
        return new ClientPortSetAttributes(attributes);
    }
}
