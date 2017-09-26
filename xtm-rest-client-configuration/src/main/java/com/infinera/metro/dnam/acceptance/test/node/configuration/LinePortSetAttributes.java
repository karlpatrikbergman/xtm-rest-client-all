package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

//TODO: Can attributes for line ports be set using configure.json? If so add methods to Node interface in the same manner
//as for client port configuration
@Value
public class LinePortSetAttributes implements MibEntryAttributes {
    private Attributes attributes;

    public void applyTo(Node node, MibEntry linePortEntry) {
        Preconditions.checkArgument(linePortEntry instanceof LinePortEntry, "Expected argument of type LinePortEntry");
        node.setLinePortAttributes((LinePortEntry) linePortEntry, attributes);
    }

    public static LinePortSetAttributes of(Attributes attributes) {
        return new LinePortSetAttributes(attributes);
    }

    public static LinePortSetAttributes of(Attribute configuration) {
        return LinePortSetAttributes.of(Attributes.of(configuration));
    }
}
