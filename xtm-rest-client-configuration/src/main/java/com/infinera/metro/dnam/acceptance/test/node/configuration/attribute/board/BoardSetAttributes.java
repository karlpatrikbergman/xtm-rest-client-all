package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import lombok.Value;

/**
 * Represents attributes that can be applied to a Board using ENM REST api "set.json"
 */
@Value
public class BoardSetAttributes implements BoardAttributes {
    private Attributes boardSetAttributes;

    public void applyTo(Node node, BoardEntry boardEntry) {
        node.setBoardAttributes(boardEntry, boardSetAttributes);
    }

    public static BoardSetAttributes of(Attributes attributes) {
        return new BoardSetAttributes(attributes);
    }

    public static BoardSetAttributes of(Attribute attribute) {
        return BoardSetAttributes.of(Attributes.of(attribute));
    }

    public static BoardSetAttributes of(String key, String value) {
        return of(Attribute.builder()
            .key(key)
            .value(value)
            .build()
        );
    }
}


