package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.OaPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.OaPortType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Oa2x17 extends AbstractBoard implements Board {
    @NonNull private final List<Port> ports;
    @JsonIgnore @NonNull private final OaPortEntry.OaPortEntryBuilder oaPortEntryBuilder;

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardAttributes","ports"})
    private Oa2x17(Subrack subrack, Slot slot, @Singular List<MibEntryAttributes> boardAttributes, List<Port> ports) {
        super(BoardType.OA2X17DBM, subrack, slot, boardAttributes);
        this.ports = ports;
        this.oaPortEntryBuilder = getOatPortEntryBuilder();
    }

    //  /oa/if/oa:1:3:1-2/set
    private OaPortEntry.OaPortEntryBuilder getOatPortEntryBuilder() {
        return OaPortEntry.builder()
            .moduleType(ModuleType.OA)
            .groupOrTableType(GroupOrTableType.IF)
            .oaPortType(OaPortType.OA)
            .subrack(getSubrack().getValue())
            .slot(getSlot().getValue());
    }
}
