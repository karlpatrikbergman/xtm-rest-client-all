package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import lombok.Value;

@Value
public class LinePortConfigAttributes implements LinePortAttributes {
    private Attributes linePortConfigAttributes;

    public void applyTo(Node node, LinePortEntry linePortEntry) {
        node.setLinePortAttributes(linePortEntry, linePortConfigAttributes);
    }

    public static LinePortConfigAttributes of(Attributes attributes) {
        return new LinePortConfigAttributes(attributes);
    }

    public static LinePortConfigAttributes of(Attribute configuration) {
        return LinePortConfigAttributes.of(Attributes.of(configuration));
    }

    public static LinePortConfigAttributes of(String key, String value) {
        return of(Attribute.builder()
            .key(key)
            .value(value)
            .build()
        );
    }
}
