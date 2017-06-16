package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

@Value
public class AddDropPortConfigAttributes implements MibEntryAttributes {
    private Configurations configurations;

    public void applyTo(Node node, MibEntry addDropPortEntry) {
        Preconditions.checkArgument(addDropPortEntry instanceof AddDropPortEntry, "Expected argument of type AddDropPortEntry");
        node.configureAddDropPortAttributes((AddDropPortEntry) addDropPortEntry, configurations);
    }

    public static AddDropPortConfigAttributes of(Configurations configurations) {
        return new AddDropPortConfigAttributes(configurations);
    }
}
