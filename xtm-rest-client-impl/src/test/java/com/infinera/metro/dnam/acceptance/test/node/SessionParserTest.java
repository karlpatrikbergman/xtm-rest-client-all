package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import org.junit.Test;

import static com.infinera.metro.dnam.acceptance.test.node.SessionIdParser.SESSION_ID_PARSER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SessionParserTest {

    @Test
    public void parseSessionIdFromHtmlForm() {
        String responseBody = new ResourceString("sessionparser/login-response-body").toString();
        assertNotNull(responseBody);
        String sessionId = SESSION_ID_PARSER.parseSessionId(responseBody);
        assertEquals("fe644b36a3cc361913c5961fd2010d6d", sessionId);
    }

    @Test
    public void parseSessionIdFromHtmlFormDuringNodeStartup() {
        String responseBody = new ResourceString("sessionparser/login-response-body-node-starting-up").toString();
        assertNotNull(responseBody);
        String sessionId = SESSION_ID_PARSER.parseSessionId(responseBody);
        assertEquals(SESSION_ID_PARSER.NODE_IS_STARTING_UP, sessionId);
    }
}
