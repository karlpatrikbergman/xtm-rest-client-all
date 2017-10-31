package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Mdu40EvenL;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.InternalConnection;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class InternalConnectionSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest {

    final LinePort linePortTx3Rx4 = LinePort.builder()
        .transmitPort(3)
        .receivePort(4)
        .build();

    final Tpd10gbe tpd10gbe = Tpd10gbe.builder()
        .subrack(Subrack.subrack1)
        .slot(Slot.slot2)
        .clientPort(ClientPort.builder()
            .transmitPort(1)
            .receivePort(2)
            .build()
        )
        .linePort(linePortTx3Rx4)
        .build();

    final ClientPort clientPortTx41_Rx42 = ClientPort.builder()
        .transmitPort(41)
        .receivePort(42)
        .build();

    final Mdu40EvenL mdu40EvenL = Mdu40EvenL.builder()
        .subrack(Subrack.subrack2)
        .slot(Slot.slot4)
        .clientPort(clientPortTx41_Rx42)
        .linePort(LinePort.builder()
            .transmitPort(81)
            .receivePort(82)
            .build())
        .build();

    final InternalConnection internalConnection = InternalConnection.builder()
        .fromPeer(tpd10gbe.getPeer(linePortTx3Rx4.getTransmitPort()))
        .toPeer(mdu40EvenL.getPeer(clientPortTx41_Rx42.getReceivePort()))
        .build();

    @Test
    public void serializeInternalConnection() throws JsonProcessingException {
        final String expectedInternalConnectionString = new ResourceString("configuration/internal_connection.yaml").toString();
        final String serializedInternalConnection = mapper.writeValueAsString(internalConnection).trim();
        assertEquals(expectedInternalConnectionString, serializedInternalConnection);
    }

    @Test
    public void deserializeInternalConnection() {
        InternalConnection deserializedInternalConnection = objectFromFileUtil.getObject("configuration/internal_connection.yaml", InternalConnection.class);
        assertEquals(internalConnection, deserializedInternalConnection);
    }
}
