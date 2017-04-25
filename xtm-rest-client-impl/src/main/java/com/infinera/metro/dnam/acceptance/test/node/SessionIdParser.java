package com.infinera.metro.dnam.acceptance.test.node;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
final class SessionIdParser {

    /**
     * A login request may return a <form> with session-id=0 during xtm startup.
     * By returning 0 we know we have to try to login again (see loginAndSetSessionId)
     */
    public static int parseSessionId(String responseBody) {
        Optional<Integer> sessionId = Pattern.compile("\\R").splitAsStream(responseBody)
                .filter(s -> s.contains("sessionId"))
                .map(s -> s.replaceFirst(".*?(\\d+).*", "$1"))
                .map(Integer::parseInt)
                .findFirst();
        if (sessionId.isPresent() && sessionId.get() != 0){
            return sessionId.get();
        } else {
            log.error("Failed to GET sessionId. responseBody {}", responseBody);
            return 0;
        }
    }
}
