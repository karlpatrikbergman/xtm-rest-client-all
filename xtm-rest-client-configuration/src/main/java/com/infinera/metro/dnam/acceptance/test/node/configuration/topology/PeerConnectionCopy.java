//package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.infinera.metro.dnam.acceptance.test.node.Node;
//import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
//import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
//import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
//import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AbstractPortEntry;
//import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
//import lombok.Builder;
//import lombok.Value;
//
////TODO: Make serialization work the same as deserialization does. It must work the same both ways
//
//@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
//@Value
//public class PeerConnectionCopy {
//    private final AbstractPortEntry localPort;
//    private final MpoIdentifier localMpoIdentifier;
//    private final AbstractPortEntry remotePort;
//    private final MpoIdentifier remoteMpoIdentifier;
//
//    @Builder
//    public PeerConnectionCopy(@JsonProperty("localPort") AbstractPortEntry localPort,
//                              @JsonProperty("localMpoIdentifier") MpoIdentifier localMpoIdentifier,
//                              @JsonProperty("remotePort") AbstractPortEntry remotePort,
//                              @JsonProperty("remoteMpoIdentifier") MpoIdentifier remoteMpoIdentifier) {
//        this.localPort = localPort;
//        this.localMpoIdentifier = localMpoIdentifier;
//        this.remotePort = remotePort;
//        this.remoteMpoIdentifier = remoteMpoIdentifier;
//    }
//
//    public void applyTo(Node nodeA, Node nodeZ) {
//        final PeerEntry localPeerEntry = getLocalPeerEntry();
//        final PeerEntry remotePeerEntry = getRemotePeerEntry();
//        final Attributes localPeerConfiguration = getPeerConfiguration(localPeerEntry, nodeZ, remotePeerEntry);
//        final Attributes remotePeerConfiguration = getPeerConfiguration(remotePeerEntry, nodeA, localPeerEntry);
//
//        nodeA.createPeer(localPeerEntry);
//        nodeA.setPeerAttributes(localPeerEntry, localPeerConfiguration);
//        nodeZ.createPeer(remotePeerEntry);
//        nodeZ.setPeerAttributes(remotePeerEntry, remotePeerConfiguration);
//    }
//
//    public PeerConnectionCopy invert() {
//        return PeerConnectionCopy.builder()
//            .localPort(this.remotePort)
//            .localMpoIdentifier(this.remoteMpoIdentifier)
//            .remotePort(this.localPort)
//            .remoteMpoIdentifier(this.localMpoIdentifier)
//            .build();
//    }
//
//    PeerEntry getLocalPeerEntry() {
//        return PeerEntry.builder()
//            .subrack(localPort.getSubrack())
//            .slot(localPort.getSlot())
//            .port(localPort.getTransmitPort())
//            .mpoIdentifier(localMpoIdentifier)
//            .build();
//    }
//
//    PeerEntry getRemotePeerEntry() {
//        return PeerEntry.builder()
//            .subrack(remotePort.getSubrack())
//            .slot(remotePort.getSlot())
//            .port(remotePort.getReceivePort())
//            .mpoIdentifier(remoteMpoIdentifier)
//            .build();
//    }
//
//    Attributes getPeerConfiguration(PeerEntry localPeerEntry, Node node, PeerEntry remotePeerEntry) {
//        return Attributes.builder()
//            .attribute(Attribute.of("topoPeerLocalLabel", localPeerEntry.getLocalLabel()))
//            .attribute(Attribute.of("topoPeerRemoteIpAddress", node.getIpAddress()))
//            .attribute(Attribute.of("topoPeerRemoteLabel", remotePeerEntry.getLocalLabel()))
//            .build();
//    }
//
//
//}
