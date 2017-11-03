package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.PeerConnectionApplier;

public class PeerConnectionApplierSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<PeerConnectionApplier> {

    public PeerConnectionApplierSerializeDeserializeTest() {
        super(PeerConnectionApplier.class, ExpectedDataFactory.INSTANCE.getPeerConnectionApplier(), "configuration/peer_connection_applier.yaml");
    }
    
}
