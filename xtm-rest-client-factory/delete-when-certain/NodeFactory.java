package com.infinera.metro.dnam.acceptance.test.factory;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;

public enum NodeFactory {
    INSTANCE;

    public Node createNode(NodeAccessData nodeAccessData) {
        return  NodeImpl.create(nodeAccessData);
    }
}
