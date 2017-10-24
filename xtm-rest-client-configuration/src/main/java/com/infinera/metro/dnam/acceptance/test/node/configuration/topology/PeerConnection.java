//package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.google.common.base.Preconditions;
//import com.infinera.metro.dnam.acceptance.test.node.Node;
//import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
//import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
//import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
//import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
//import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
//import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
//import lombok.Builder;
//import lombok.NonNull;
//import lombok.Value;
//
////TODO: Make serialization work the same as deserialization does. It must work the same both ways
//
//@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
//@Value
//public class PeerConnection {
//    @NonNull private final PeerEntry localPeerEntry;
//    @NonNull private final PeerEntry remotePeerEntry;
//    private final Attributes localPeerConfiguration;
//    private final Attributes remotePeerConfiguration;
//
//    @JsonCreator
//    @Builder
//    public PeerConnection(@JsonProperty("localNodeIpAddress") String localNodeIpAddress,
//                          @JsonProperty("localBoard") BoardEntry localBoardEntry,
//                          @JsonProperty("localPort") Port localPort,
//                          @JsonProperty("localMpoIdentifier") MpoIdentifier localMpoIdentifier,
//                          @JsonProperty("remoteNodeIpAddress") String remoteNodeIpAddress,
//                          @JsonProperty("remoteBoard") BoardEntry remoteBoardEntry,
//                          @JsonProperty("remotePort") Port remotePort,
//                          @JsonProperty("remoteMpoIdentifier") MpoIdentifier remoteMpoIdentifier) {
//
//        Preconditions.checkNotNull(localNodeIpAddress, "localNodeIpAddress can't be null");
//        Preconditions.checkNotNull(localBoardEntry, "localBoardEntry can't be null");
//        Preconditions.checkNotNull(localPort, "localPort can't be null");
//        Preconditions.checkNotNull(localMpoIdentifier, " can't be null");
//        Preconditions.checkNotNull(remoteNodeIpAddress, "remoteNodeIpAddress can't be null");
//        Preconditions.checkNotNull(remoteBoardEntry, "remoteBoardEntry can't be null");
//        Preconditions.checkNotNull(remotePort, "remotePort can't be null");
//        Preconditions.checkNotNull(remoteMpoIdentifier, "remoteMpoIdentifier can't be null");
//
//        this.localPeerEntry = PeerEntry.builder()
//            .subrack(localBoardEntry.getSubrack())
//            .slot(localBoardEntry.getSlot())
//            .port(localPort.getTransmitPort())
//            .mpoIdentifier(localMpoIdentifier)
//            .build();
//        this.remotePeerEntry = PeerEntry.builder()
//            .subrack(remoteBoardEntry.getSubrack())
//            .slot(remoteBoardEntry.getSlot())
//            .port(remotePort.getReceivePort())
//            .mpoIdentifier(remoteMpoIdentifier)
//            .build();
//        this.localPeerConfiguration = Attributes.builder()
//            .attribute(
//                Attribute.builder()
//                    .key("topoPeerLocalLabel")
//                    .value(localPeerEntry.getLocalLabel())
//                    .build()
//            )
//            .attribute(
//                Attribute.builder()
//                    .key("topoPeerRemoteIpAddress")
//                    .value(remoteNodeIpAddress) //This node is not expected to be running in this test
//                    .build()
//            )
//            .attribute(
//                Attribute.builder()
//                    .key("topoPeerRemoteLabel")
//                    .value(remotePeerEntry.getLocalLabel())
//                    .build()
//            )
//            .build();
//        this.remotePeerConfiguration = Attributes.builder()
//            .attribute(
//                Attribute.builder()
//                    .key("topoPeerLocalLabel")
//                    .value(remotePeerEntry.getLocalLabel())
//                    .build()
//            )
//            .attribute(
//                Attribute.builder()
//                    .key("topoPeerRemoteIpAddress")
//                    .value(localNodeIpAddress)
//                    .build()
//            )
//            .attribute(
//                Attribute.builder()
//                    .key("topoPeerRemoteLabel")
//                    .value(localPeerEntry.getLocalLabel())
//                    .build()
//            )
//            .build();
//    }
//
//    public void applyTo(Node nodeA, Node nodeZ) {
//        nodeA.createPeer(localPeerEntry);
//        nodeA.setPeerAttributes(localPeerEntry, localPeerConfiguration);
//        nodeZ.createPeer(remotePeerEntry);
//        nodeZ.setPeerAttributes(remotePeerEntry, remotePeerConfiguration);
//    }
//}
