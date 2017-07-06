package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import lombok.Value;

@Value
public class PeerEntrySetAttributes implements MibEntryAttributes {
    private Configurations configurations;

    public void applyTo(Node node, MibEntry peerEntry) {
        Preconditions.checkArgument(peerEntry instanceof PeerEntry, "Expected argument of type PeerEntry");
        node.setPeerAttributes((PeerEntry) peerEntry, configurations);
    }

    public static PeerEntrySetAttributes of(Configurations configurations) {
        return new PeerEntrySetAttributes(configurations);
    }
}
