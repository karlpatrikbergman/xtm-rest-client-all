package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

//TODO: Find a better name?
@EqualsAndHashCode
@AllArgsConstructor
public class AbstractNodesApplier {
    @NonNull
    protected final NodeAccessData fromNodeAccessData;
    @NonNull
    protected final NodeAccessData toNodeAccessData;

    Node getFromNode() {
        return NodeImpl.create(fromNodeAccessData);
    }

    Node getToNode() {
        return NodeImpl.create(toNodeAccessData);
    }
}
