package com.infinera.metro.dnam.acceptance.test.node.configuration.board.mib.line;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.LinePortBoard;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;

public interface MUX_IF_MUX_LinePortBoard extends LinePortBoard {
    @JsonIgnore
    default LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
            .moduleType(ModuleType.MUX)
            .groupOrTableType(GroupOrTableType.IF)
            .linePortType(LinePortType.MUX)
            .subrack(getSubrack().getValue())
            .slot(getSlot().getValue());
    }
}
