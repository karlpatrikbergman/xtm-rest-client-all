package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import lombok.Value;

@Value
public class LinePortSetAttributes implements LinePortAttributes {
    private Attributes linePortSetAttributes;

    public void applyTo(Node node, LinePortEntry linePortEntry) {
        node.setLinePortAttributes(linePortEntry, linePortSetAttributes);
    }

    public static LinePortSetAttributes of(Attributes attributes) {
        return new LinePortSetAttributes(attributes);
    }

    public static LinePortSetAttributes of(Attribute configuration) {
        return LinePortSetAttributes.of(Attributes.of(configuration));
    }

    public static LinePortSetAttributes of(String key, String value) {
        return of(Attribute.builder()
            .key(key)
            .value(value)
            .build()
        );
    }
}
