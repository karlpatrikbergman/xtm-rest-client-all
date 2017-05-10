package com.infinera.metro.dnam.acceptance.test.node.config;

import com.infinera.metro.dnam.acceptance.test.mib.Board;
import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.mib.LinePortEntry;
import lombok.Value;

@Value
public class NodeEquipment {
    private final String name;
    private final Board board;
    private final BoardEntry boardEntry;
    private final LinePortEntry linePortEntry;
}
