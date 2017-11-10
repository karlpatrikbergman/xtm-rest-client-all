package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.Peer;

public class PeerSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<Peer> {

    public PeerSerializeDeserializeTest() {
        super(Peer.class, ExpectedTestDataFactory.INSTANCE.getPeer(), "configuration/peer.yaml");
    }
}
