package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

/**
 * Represents attributes that can be applied to a Board using ENM REST api "set.json"
 */
@Value
public class BoardSetAttributes implements MibEntryAttributes {
    private Attributes attributes;

    public void applyTo(Node node, MibEntry boardEntry) {
        Preconditions.checkArgument(boardEntry instanceof BoardEntry, "Expected argument of type BoardEntry");
        node.setBoardAttributes((BoardEntry) boardEntry, attributes);
    }

    public static BoardSetAttributes of(Attributes attributes) {
        return new BoardSetAttributes(attributes);
    }

    public static BoardSetAttributes of(Attribute attribute) {
        return BoardSetAttributes.of(Attributes.of(attribute));
    }
}


