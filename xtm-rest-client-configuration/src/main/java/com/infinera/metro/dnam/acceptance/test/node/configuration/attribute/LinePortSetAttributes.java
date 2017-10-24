package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import lombok.Value;

//TODO: Can attributes for line ports be set using configure.json? If so add methods to Node interface in the same manner
//as for client port configuration
@Value
public class LinePortSetAttributes implements LinePortAttributes {
    private Attributes attributes;

    public void applyTo(Node node, LinePortEntry linePortEntry) {
        node.setLinePortAttributes(linePortEntry, attributes);
    }

    public static LinePortSetAttributes of(Attributes attributes) {
        return new LinePortSetAttributes(attributes);
    }

    public static LinePortSetAttributes of(Attribute configuration) {
        return LinePortSetAttributes.of(Attributes.of(configuration));
    }
}
