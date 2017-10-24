package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;


public interface ROADM_ADDDROPIF_ADDDROP_AddDropPortBoard extends AddDropPortBoard {
    @JsonIgnore
    default AddDropPortEntry.AddDropPortEntryBuilder getAddDropPortEntryBuilder() {
        return AddDropPortEntry.builder()
            .moduleType(ModuleType.ROADM)
            .groupOrTableType(GroupOrTableType.ADD_DROP_IF)
            .clientPortType(ClientPortType.ADD_DROP)
            .subrack(getSubrack().getValue())
            .slot(getSlot().getValue());
    }
    Subrack getSubrack();
    Slot getSlot();
}
