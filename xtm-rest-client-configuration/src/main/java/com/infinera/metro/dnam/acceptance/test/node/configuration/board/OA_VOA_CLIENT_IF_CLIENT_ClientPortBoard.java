package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;

public interface OA_VOA_CLIENT_IF_CLIENT_ClientPortBoard extends ClientPortBoard {
    @JsonIgnore
    default ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder() {
        return ClientPortEntry.builder()
            .moduleType(ModuleType.OA)
            .groupOrTableType(GroupOrTableType.VOA_CLIENT_IF)
            .clientPortType(ClientPortType.CLIENT)
            .subrack(getSubrack().getValue())
            .slot(getSlot().getValue());
    }
}
