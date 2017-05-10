package com.infinera.metro.dnam.acceptance.test.node.config;

import com.infinera.metro.dnam.acceptance.test.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.IOException;

@Builder
@Value
public class NodeConfig {
    @NonNull Node node;
    @NonNull MpoIdentifier mpoIdentifier;
    @NonNull BoardEntry boardEntry;
    @NonNull LinePortEntry linePortEntry;
    @NonNull Configuration linePortConfiguration;
    @NonNull ClientPortEntry clientPortEntry;
    @NonNull Configuration clientPortConfiguration;
//    @NonNull PeerEntry peerEntry;
//    @NonNull ParameterList peerConfiguration;

    public void apply() throws IOException {
        node.createBoard(boardEntry);
        node.setClientPortConfiguration(clientPortEntry, ParameterList.of(clientPortConfiguration));
        node.setLinePortConfiguration(linePortEntry, ParameterList.of(linePortConfiguration));
//        node.createLocalPeer(peerEntry);
//        node.setLocalPeerConfiguration(peerEntry, peerConfiguration);
    }

}
