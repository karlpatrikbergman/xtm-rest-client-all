package com.infinera.metro.dnam.acceptance.test.node.mib;

import java.util.Arrays;

public enum MibObjectFactory {
    MIB_OBJECT_FACTORY;

    private static final String DEFAULT_NODE_A_IP_ADDRESS = "172.17.0.2";
    private static final String DEFAULT_NODE_Z_IP_ADDRESS = "172.17.0.3";

    public static ParameterList buildConfigurePeerParameterList(PeerEntry peerEntry) {
        return ParameterList.builder()
                .parameterList(Arrays.asList(
                        Configuration.builder()
                                .key("topoPeerLocalLabel")
                                .value(peerEntry.getPeerLocalLabel())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteIpAddress")
                                .value(peerEntry.getPeerRemoteIpAddress())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteLabel")
                                .value(peerEntry.getPeerRemoteLabel())
                                .build()
                ))
                .build();
    }

    public static BoardEntry createBoardEntry(Board board) {
        return BoardEntry.builder()
                .board(board)
                .subrack(1)
                .slot(2)
                .build();
    }

    /**
     * The PeerEntry created here will be used to create a transmit peer entry on Node A (172.17.0.2)
     * Local XTM version is below 27, so no MTO identifier is added to peer name
     * Local peer entry name will be peer:1:2:3
     *
     */
    public static PeerEntry createDefaultTransmitPeerEntryNodeA() {
        return createDefaultTransmitPeerEntryNodeA(DEFAULT_NODE_Z_IP_ADDRESS);
    }

    private static PeerEntry createDefaultTransmitPeerEntryNodeA(String remoteNodeIpAddress) {
        return PeerEntry.builder()
                .localLinePortEntry(createLinePortEntryNodeA())
                .remoteLinePortEntry(createLinePortEntryNodeZ())
                .remoteNodeIpAddress(remoteNodeIpAddress)
                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .isTransmitSide(true)
                .build();
    }

    /**
     * The PeerEntry created here will be used to create a receive peer entry on Node Z (172.17.0.3)
     * Local XTM version is higher than 27, so MTO identifier is added to local peer name
     * Local peer entry name will be peer:2:3:0:8
     *
     */
    public static PeerEntry createDefaultReceivePeerEntryNodeZ() {
        return createDefaultReceivePeerEntryNodeZ(DEFAULT_NODE_A_IP_ADDRESS);
    }

    private static PeerEntry createDefaultReceivePeerEntryNodeZ(String remoteNodeIpAddress) {
        return PeerEntry.builder()
                .localLinePortEntry(createLinePortEntryNodeZ())
                .remoteLinePortEntry(createLinePortEntryNodeA())
                .remoteNodeIpAddress(remoteNodeIpAddress)
                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .isTransmitSide(false)
                .build();
    }

    /**
     * At some point during test setup this line port entry is created in Node A: 172.17.0.2
     * XTM version < 27 therefore MPO identifier not is used.
     * The transmit port will be used when creating transmitting peer on Node A (peer:1:2:3):
     *      172.17.0.2/mib/topo/peer/peer:1:2:3/create.json
     */
    private static LinePortEntry createLinePortEntryNodeA() {
        return LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(3)
                .receivePort(4)
                .build();
    }

    /**
     * At some point during test setup this line port entry is created in Node Z: 172.17.0.3
     * XTM version >= 27 therefore MPO identifier is used.
     * The transmit port will be used when creating transmitting peer on Node Z (peer:1:2:0:3):
     *      172.17.0.3/mib/topo/peer/peer:1:2:0:3/create.json
     */
    private static LinePortEntry createLinePortEntryNodeZ() {
        return LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(7)
                .receivePort(8)
                .build();
    }
}
