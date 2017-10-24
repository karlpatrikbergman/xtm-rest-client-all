package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;
import lombok.Value;

@Value
public class AddDropPortConfigAttributes implements AddDropPortAttributes {
    private Attributes attributes;

    public void applyTo(Node node, AddDropPortEntry addDropPortEntry) {
        node.configureAddDropPortAttributes(addDropPortEntry, attributes);
    }

    public static AddDropPortConfigAttributes of(Attributes attributes) {
        return new AddDropPortConfigAttributes(attributes);
    }
}
