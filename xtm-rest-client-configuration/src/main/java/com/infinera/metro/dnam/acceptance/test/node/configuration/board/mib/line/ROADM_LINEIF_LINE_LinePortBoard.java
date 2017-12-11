package com.infinera.metro.dnam.acceptance.test.node.configuration.board.mib.line;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.LinePortBoard;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;


public interface ROADM_LINEIF_LINE_LinePortBoard extends LinePortBoard {
    @JsonIgnore
    default LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
            .moduleType(ModuleType.ROADM)
            .groupOrTableType(GroupOrTableType.LINE_IF)
            .linePortType(LinePortType.LINE)
            .subrack(getSubrack().getValue())
            .slot(getSlot().getValue());
    }
    Subrack getSubrack();
    Slot getSlot();
}
