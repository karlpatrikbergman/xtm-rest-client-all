package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.InternalConnectionApplier;

public class InternalConnectionApplierSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<InternalConnectionApplier> {

    public InternalConnectionApplierSerializeDeserializeTest() {
        super(InternalConnectionApplier.class, ExpectedDataFactory.INSTANCE.getInternalConnectionApplier(), "configuration/internal_connection_applier.yaml");
    }

}
