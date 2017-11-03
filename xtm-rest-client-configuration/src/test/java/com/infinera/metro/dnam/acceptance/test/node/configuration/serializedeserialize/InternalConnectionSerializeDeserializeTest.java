package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.InternalConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalConnectionSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<InternalConnection> {

    public InternalConnectionSerializeDeserializeTest() {
        super(InternalConnection.class, ExpectedDataFactory.INSTANCE.getInternalConnection(), "configuration/internal_connection.yaml");
    }

}
