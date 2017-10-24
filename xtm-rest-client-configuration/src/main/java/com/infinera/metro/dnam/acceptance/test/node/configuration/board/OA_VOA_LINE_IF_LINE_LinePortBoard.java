package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;

public interface OA_VOA_LINE_IF_LINE_LinePortBoard extends LinePortBoard {
    @JsonIgnore
    default ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder() {
        return ClientPortEntry.builder()
            .moduleType(ModuleType.OA)
            .groupOrTableType(GroupOrTableType.VOA_LINE_IF)
            .clientPortType(ClientPortType.CLIENT)
            .subrack(getSubrack().getValue())
            .slot(getSlot().getValue());
    }
}
