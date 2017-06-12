package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.PeerType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PeerEntry implements MibEntry {
    @NonNull private final ModuleType moduleType = ModuleType.TOPO;
    @NonNull private final GroupOrTableType groupOrTableType = GroupOrTableType.PEER;
    @NonNull private final PeerType peerType = PeerType.PEER;
    @NonNull private final LinePortEntry localLinePortEntry;
    @NonNull private final LinePortEntry remoteLinePortEntry;
    @NonNull private final String nodeIpAddress;
    @NonNull private final String remoteNodeIpAddress;
    @NonNull private final MpoIdentifier localMpoIdentifier;
    @NonNull private final MpoIdentifier remoteMpoIdentifier;
    @NonNull private final Boolean isTransmitSide;

    public String getPeerLocalLabel() {
        return MibPathUtil.MIB_PATH_UTIL.getPeerLabel(localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), getLocalPort(), localMpoIdentifier);
    }

    public String getPeerRemoteIpAddress() {
        return remoteNodeIpAddress;
    }

    public String getPeerRemoteLabel() {
        return MibPathUtil.MIB_PATH_UTIL.getPeerLabel(remoteLinePortEntry.getSubrack(), remoteLinePortEntry.getSlot(), getPeerRemotePort(), remoteMpoIdentifier);
    }

    @Override
    public String getMibEntryString() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (peerType.getName(), localLinePortEntry.getSubrack(),
                localLinePortEntry.getSlot(), getLocalPort(), localMpoIdentifier);
    }

    @Override
    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(moduleType, groupOrTableType, this);
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
