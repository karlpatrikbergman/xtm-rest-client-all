package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import lombok.Value;

@Value
public class ClientPortSetAttributes implements ClientPortAttributes {
    private Attributes clientPortSetAttributes;

    public void applyTo(Node node, ClientPortEntry clientPortEntry) {
        node.setClientPortAttributes(clientPortEntry, clientPortSetAttributes);
    }

    public static ClientPortSetAttributes of(Attributes attributes) {
        return new ClientPortSetAttributes(attributes);
    }

    public static ClientPortSetAttributes of(Attribute attribute) {
        return ClientPortSetAttributes.of(Attributes.of(attribute));
    }

    public static ClientPortSetAttributes of(String key, String value) {
        return of(Attribute.builder()
            .key(key)
            .value(value)
            .build()
        );
    }
}
