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
    @NonNull private final String remoteNodeIpAddress;
    @NonNull private final Boolean isTransmitSide;

    public String getLocalLabel() {
        return MIB_PATH_UTIL.getPeerLabel(localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), getLocalPort());
    }

    public String getPeerRemoteIpAddress() {
        return remoteNodeIpAddress;
    }

    public int getPeerRemoteSubrack() {
        return remoteLinePortEntry.getSubrack();
    }

    public int getPeerRemoteSlot() {
        return remoteLinePortEntry.getSlot();
    }

    public int getPeerRemotePort() {
        return remoteLinePortEntry.getReceiverPort();
    }

    public String getPeerRemoteLabel() {
        return MIB_PATH_UTIL.getPeerLabel(remoteLinePortEntry.getSubrack(), remoteLinePortEntry.getSlot(),
                getRemotePort());
    }

    @Override
    public String getMibEntryString() {
        return MIB_PATH_UTIL.getMibEntryString (peer.getName(), localLinePortEntry.getSubrack(),
                localLinePortEntry.getSlot(), getLocalPort());
    }

    @Override
    public String getMibEntryPath() {
        return MIB_PATH_UTIL.getMibEntryPath(module, groupOrTable, this);
    }

    private int getLocalPort() {
        return (isTransmitSide) ? localLinePortEntry.getTransmitterPort() : localLinePortEntry.getReceiverPort();
    }

    private int getRemotePort() {
        return (isTransmitSide) ? remoteLinePortEntry.getReceiverPort() : remoteLinePortEntry.getTransmitterPort();
    }

}
