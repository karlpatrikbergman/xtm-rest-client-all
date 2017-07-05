package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;

public interface CLIENT_IF_CLIENT_ClientPortBoard extends ClientPortBoard {
    @JsonIgnore
    default ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder() {
        return ClientPortEntry.builder()
            .moduleType(ModuleType.CLIENT)
            .groupOrTableType(GroupOrTableType.IF)
            .clientPortType(ClientPortType.CLIENT)
            .subrack(getSubrack())
            .slot(getSlot().getValue());
    }
    Integer getSubrack();
    Slot getSlot();
}
