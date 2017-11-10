package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;

public class Tpd10gbeSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<Tpd10gbe> {

    public Tpd10gbeSerializeDeserializeTest() {
        super(Tpd10gbe.class, ExpectedTestDataFactory.INSTANCE.getTpd10Gbe(), "configuration/tpd10gbe.yaml");
    }
}
