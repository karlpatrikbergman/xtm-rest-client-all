package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class InternalConnectionTest {

    //int:1:2:0:3:1:4:0:4
    private final AddDropPortEntry fromPort =AddDropPortEntry.builder()
        .moduleType(ModuleType.ROADM)
        .groupOrTableType(GroupOrTableType.ADD_DROP_IF)
        .clientPortType(ClientPortType.ADD_DROP)
        .subrack(1)
        .slot(2)
        .transmitPort(3)
        .receivePort(4)
        .build();

    private final AddDropPortEntry toPort = AddDropPortEntry.builder()
        .moduleType(ModuleType.ROADM)
        .groupOrTableType(GroupOrTableType.ADD_DROP_IF)
        .clientPortType(ClientPortType.ADD_DROP)
        .subrack(1)
        .slot(4)
        .transmitPort(3)
        .receivePort(4)
        .build();

    private final InternalConnection internalConnection = InternalConnection.builder()
        .fromPort(fromPort)
        .fromMpoIdentifier(MpoIdentifier.NotPresent())
        .toPort(toPort)
        .toMpoIdentifier(MpoIdentifier.NotPresent())
        .build();

    @Test
    public void testInternalconnectionMibPath() {
        final String actualInternalConnectionMibPath = internalConnection.getInternalConnectionEntry().getMibEntryPath();
        assertEquals("/mib/topo/internal/int:1:2:0:3:1:4:0:4", actualInternalConnectionMibPath);
        log.info(actualInternalConnectionMibPath);
    }
}
