package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.adddrop;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;
import lombok.Value;

@Value
public class AddDropPortConfigAttributes implements AddDropPortAttributes {
    private Attributes addDropPortConfigAttributes;

    public void applyTo(Node node, AddDropPortEntry addDropPortEntry) {
        node.configureAddDropPortAttributes(addDropPortEntry, addDropPortConfigAttributes);
    }

    public static AddDropPortConfigAttributes of(Attributes attributes) {
        return new AddDropPortConfigAttributes(attributes);
    }

    public static AddDropPortConfigAttributes of(Attribute configuration) {
        return AddDropPortConfigAttributes.of(Attributes.of(configuration));
    }

    public static AddDropPortConfigAttributes of(String key, String value) {
        return of(Attribute.builder()
            .key(key)
            .value(value)
            .build()
        );
    }
}