package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.Value;

/**
 * Represents attributes that can be applied to a Board using ENM REST api "set.json"
 */
@Value
public class BoardSetAttributes implements MibEntryAttributes {
    private Configurations configurations;

    public void applyTo(Node node, MibEntry boardEntry) {
        Preconditions.checkArgument(boardEntry instanceof BoardEntry, "Expected argument of type BoardEntry");
        node.setBoardAttributes((BoardEntry) boardEntry, configurations);
    }

    public static BoardSetAttributes of(Configurations configurations) {
        return new BoardSetAttributes(configurations);
    }

    public static BoardSetAttributes of(Configuration configuration) {
        return BoardSetAttributes.of(Configurations.of(configuration));
    }
}


