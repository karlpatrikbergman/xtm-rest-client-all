package com.infinera.metro.dnam.acceptance.test.node;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * <form action="" name="sessionForm" method="post">
 * <p>
 * <input type="hidden" name="neUserNodeName" value="" />
 * <input type="hidden" name="sessionId" value="(null)" />
 * <input type="hidden" name="rcText" value='NOK' />
 * <input type="hidden" name="rcText2" value='The node is starting up. Please wait and try to login later.' />
 * <input type="hidden" name="inactivityTimeout" value="3600" />
 * <input type="hidden" name="culess" value="false" /><
 * input type="hidden" name="oldCu" value="false" />
 * <input type="hidden" name="user" value='root' />
 * </p>
 * </form>
 */



@Slf4j
public enum SessionIdParser {
    SESSION_ID_PARSER;

    public static final String NODE_IS_STARTING_UP = "Node is starting up";

    public String parseSessionId(String responseBody) {
        Preconditions.checkNotNull(responseBody, "Failed to retrieve session id. Response body cannot be null");

        final Document doc = Jsoup.parse(responseBody);
        final String rcTextValue = doc.select("input[name=rcText]")
            .first()
            .attr("value");
        final String rcText2Value = doc.select("input[name=rcText2]")
            .first()
            .attr("value");

        if(rcTextValue.equals("NOK") || rcText2Value.equals("The node is starting up. Please wait and try to login later.")) {
            return NODE_IS_STARTING_UP;
        } else {
            final Element sessionIdInputTag = doc.select("input[name=sessionId]").first();
            return  sessionIdInputTag.attr("value");
        }
    }
}
