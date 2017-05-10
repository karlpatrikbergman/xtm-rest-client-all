package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static com.infinera.metro.dnam.acceptance.test.mib.util.MibPathUtil.MIB_PATH_UTIL;

@Value
@Builder
public class PeerEntry implements MibEntry {
    @NonNull private final Module module = Module.TOPO;
    @NonNull private final GroupOrTable groupOrTable = GroupOrTable.PEER;
    @NonNull private final Peer peer = Peer.PEER;
    @NonNull private final LinePortEntry localLinePortEntry;
    @NonNull private final LinePortEntry remoteLinePortEntry;
    @NonNull private final String nodeIpAddress;
    @NonNull private final String remoteNodeIpAddress;
    @NonNull private final MpoIdentifier localMpoIdentifier;
    @NonNull private final MpoIdentifier remoteMpoIdentifier;
    @NonNull private final Boolean isTransmitSide;

    public String getPeerLocalLabel() {
        return MIB_PATH_UTIL.getPeerLabel(localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), getLocalPort(), localMpoIdentifier);
    }

    public String getPeerRemoteIpAddress() {
        return remoteNodeIpAddress;
    }

    public String getPeerRemoteLabel() {
        return MIB_PATH_UTIL.getPeerLabel(remoteLinePortEntry.getSubrack(), remoteLinePortEntry.getSlot(), getPeerRemotePort(), remoteMpoIdentifier);
    }

    @Override
    public String getMibEntryString() {
        return MIB_PATH_UTIL.getMibEntryString (peer.getName(), localLinePortEntry.getSubrack(),
                localLinePortEntry.getSlot(), getLocalPort(), localMpoIdentifier);
    }

    @Override
    public String getMibEntryPath() {
        return MIB_PATH_UTIL.getMibEntryPath(module, groupOrTable, this);
    }

    private int getLocalPort() {
        return (isTransmitSide) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
    }

    private int getPeerRemotePort() {
        return (isTransmitSide) ? remoteLinePortEntry.getReceivePort() : remoteLinePortEntry.getTransmitPort();
    }

    public PeerEntry invert() {
        return PeerEntry.builder()
                .localLinePortEntry(remoteLinePortEntry)
                .remoteLinePortEntry(localLinePortEntry)
                .nodeIpAddress(remoteNodeIpAddress)
                .remoteNodeIpAddress(nodeIpAddress)
                .localMpoIdentifier(remoteMpoIdentifier)
                .remoteMpoIdentifier(localMpoIdentifier)
                .isTransmitSide(!isTransmitSide)
                .build();
    }
}
