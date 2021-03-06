package com.infinera.metro.dnam.acceptance.test.node.configuration.board.mib.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.ClientPortBoard;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;

public interface MRT_IF_MRT_ClientPortBoard extends ClientPortBoard {
    @JsonIgnore
    default ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder() {
        return ClientPortEntry.builder()
            .moduleType(ModuleType.MRT)
            .groupOrTableType(GroupOrTableType.IF)
            .clientPortType(ClientPortType.MRT)
            .subrack(getSubrack().getValue())
            .slot(getSlot().getValue());
    }
}
