package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;

public class PeerConnectionSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<PeerConnection> {

    public PeerConnectionSerializeDeserializeTest() {
        super(PeerConnection.class, ExpectedTestDataFactory.INSTANCE.getPeerConnection(), "configuration/peer_connection.yaml");
    }

}
