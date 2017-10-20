package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

@Value
public class AddDropPortConfigAttributes implements MibEntryAttributes {
    private Attributes attributes;

    public void applyTo(Node node, MibEntry addDropPortEntry) {
        Preconditions.checkArgument(addDropPortEntry instanceof AddDropPortEntry, "Expected argument of type AddDropPortEntry");
        node.configureAddDropPortAttributes((AddDropPortEntry) addDropPortEntry, attributes);
    }

    public static AddDropPortConfigAttributes of(Attributes attributes) {
        return new AddDropPortConfigAttributes(attributes);
    }
}
