package com.infinera.metro.dnam.acceptance.test.node.configuration.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtilJackson;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * Doesn't actually test anything
 */
@Slf4j
public class PeerConnectionSerializationTest {

    private final String ipAddressNodeA = "172.17.0.2";
    private final String ipAddressNodeZ = "172.17.0.3";

    private final LinePortEntry linePortEntryNodeA = LinePortEntry.builder()
        .moduleType(ModuleType.WDM)
        .groupOrTableType(GroupOrTableType.IF)
        .linePortType(LinePortType.WDM)
        .subrack(1)
        .slot(2)
        .transmitPort(3)
        .receivePort(4)
        .build();

    private final LinePortEntry linePortEntryNodeZ = LinePortEntry.builder()
        .moduleType(ModuleType.WDM)
        .groupOrTableType(GroupOrTableType.IF)
        .linePortType(LinePortType.WDM)
        .subrack(2)
        .slot(3)
        .transmitPort(7)
        .receivePort(8)
        .build();


    @Test
    public void test() throws IOException {
        PeerConnection peerConnectionAtoZ = PeerConnection.builder()
            .localNodeIpAddress(ipAddressNodeA)
            .localPortEntry(linePortEntryNodeA)
            .localMpoIdentifier(MpoIdentifier.NotPresent())
            .remoteNodeIpAddress(ipAddressNodeZ)
            .remotePortEntry(linePortEntryNodeZ)
            .remoteMpoIdentifier(MpoIdentifier.NotPresent())
            .build();
        ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();
        log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(peerConnectionAtoZ));
    }
}
