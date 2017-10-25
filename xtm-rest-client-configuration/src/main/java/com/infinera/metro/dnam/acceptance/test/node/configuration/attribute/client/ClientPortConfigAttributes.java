package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import lombok.Value;

@Value
public class ClientPortConfigAttributes implements ClientPortAttributes {
    private Attributes clientPortConfigAttributes;

    public void applyTo(Node node, ClientPortEntry clientPortEntry) {
        node.configureClientPortAttributes(clientPortEntry, clientPortConfigAttributes);
    }

    public static ClientPortConfigAttributes of(Attributes attributes) {
        return new ClientPortConfigAttributes(attributes);
    }

    public static ClientPortConfigAttributes of(Attribute attribute) {
        return ClientPortConfigAttributes.of(Attributes.of(attribute));
    }

    public static ClientPortConfigAttributes of(String key, String value) {
        return of(Attribute.builder()
            .key(key)
            .value(value)
            .build()
        );
    }
}
