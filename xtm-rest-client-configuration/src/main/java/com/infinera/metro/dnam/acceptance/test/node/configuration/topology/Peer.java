package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.PortUtil;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Peer {
    private final Subrack subrack;
    private final Slot slot;
    private final MpoIdentifier mpoIdentifier;
    private final Integer port;

    public Peer invert() {
        return Peer.builder()
            .subrack(this.getSubrack())
            .slot(this.getSlot())
            .mpoIdentifier(this.getMpoIdentifier())
            .port(PortUtil.PORT_UTIL.reversePort(this.getPort()))
            .build();
    }
}


