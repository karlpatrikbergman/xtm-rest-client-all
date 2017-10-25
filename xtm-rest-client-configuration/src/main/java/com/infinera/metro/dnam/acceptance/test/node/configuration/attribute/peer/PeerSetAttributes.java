package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.peer;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import lombok.Value;

@Value
public class PeerSetAttributes implements PeerAttributes {
    private Attributes peerSetAttributes;

    public void applyTo(Node node, PeerEntry peerEntry) {
        node.setPeerAttributes(peerEntry, peerSetAttributes);
    }

    public static PeerSetAttributes of(Attributes attributes) {
        return new PeerSetAttributes(attributes);
    }

    public static PeerSetAttributes of(Attribute attribute) {
        return PeerSetAttributes.of(Attributes.of(attribute));
    }

    public static PeerSetAttributes of(String key, String value) {
        return of(Attribute.builder()
            .key(key)
            .value(value)
            .build()
        );
    }
}
