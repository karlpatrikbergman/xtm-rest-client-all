package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import lombok.Value;

@Value
public class ClientPortSetAttributes implements ClientPortAttributes {
    private Attributes attributes;

    public void applyTo(Node node, ClientPortEntry clientPortEntry) {
        node.setClientPortAttributes(clientPortEntry, attributes);
    }

    public static ClientPortSetAttributes of(Attributes attributes) {
        return new ClientPortSetAttributes(attributes);
    }
}
