package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;

public interface WDM_IF_WDM_LinePortBoard extends LinePortBoard {
    @JsonIgnore
    default LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
            .moduleType(ModuleType.WDM)
            .groupOrTableType(GroupOrTableType.IF)
            .linePortType(LinePortType.WDM)
            .subrack(getSubrack().getValue())
            .slot(getSlot().getValue());
    }
}
