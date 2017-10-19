package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * TODO: Actually test something
 */
@Slf4j
public class PeerConnectionDeserializationTest extends  YamlDeserializationTest {

    @Test
    public void test() {
        PeerConnection peerConnection = objectFromFileUtil.getObject("configuration/peer_connection.yaml", PeerConnection.class);
        assertNotNull(peerConnection);
        log.info(peerConnection.toString());
    }
}
