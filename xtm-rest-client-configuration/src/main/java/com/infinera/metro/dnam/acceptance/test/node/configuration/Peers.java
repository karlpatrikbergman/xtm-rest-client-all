package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

@AllArgsConstructor
@Value
public class Peers {
    @NonNull
    private final String fromNode;
    @NonNull
    private final String toNode;

    public Peers(String toNodeFromNodeString) {
        Preconditions.checkArgument(Peers.isValidPeersConstructorStringArgument(toNodeFromNodeString), "Constructor " +
            "string argument must have forma 'string_string'. Example 'nodeA_nodeZ'");
        String[] toNodeFromNodeStringArray = toNodeFromNodeString.split("_");
        this.fromNode= toNodeFromNodeStringArray[0];
        this.toNode= toNodeFromNodeStringArray[1];
    }

    @Override
    @JsonValue
    public String toString() {
        return fromNode + "_" + toNode;
    }

    @JsonIgnore
    public static Peers of(String fromNode, String toNode) {
        return new Peers(fromNode, toNode);
    }

    @JsonIgnore
    public Peers invert() {
        return new Peers(this.toNode, this.fromNode);

    }

    static boolean isValidPeersConstructorStringArgument(String string) {
        final String PATTERN = "^[A-Za-z0-9-\\+]+_+[A-Za-z0-9-]+$";
        final Pattern pattern = Pattern.compile(PATTERN);
        return pattern.matcher(string).matches();
    }
}
