package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import lombok.Value;

@Value
public class ClientPortConfigAttributes implements ClientPortAttributes {
    private Attributes attributes;

    public void applyTo(Node node, ClientPortEntry clientPortEntry) {
        node.configureClientPortAttributes(clientPortEntry, attributes);
    }

    public static ClientPortConfigAttributes of(Attributes attributes) {
        return new ClientPortConfigAttributes(attributes);
    }
}
