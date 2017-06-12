package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Port;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Oa2x17 extends AbstractBoard implements Board {
    @NonNull private final List<Port> ports;
    @JsonIgnore @NonNull private final OaPortEntry.OaPortEntryBuilder oaPortEntryBuilder;

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "ports"})
    private Oa2x17(Integer subrack, Slot slot, List<Port> ports) {
        super(BoardType.OA2X17DBM, subrack, slot);
        this.ports = ports;
        this.oaPortEntryBuilder = getOatPortEntryBuilder();
    }

    @Override
    public void applyTo(Node node) {
        createBoard(node);
    }

    //  /oa/if/oa:1:3:1-2/set
    private OaPortEntry.OaPortEntryBuilder getOatPortEntryBuilder() {
        return OaPortEntry.builder()
            .moduleType(ModuleType.OA)
            .groupOrTableType(GroupOrTableType.IF)
            .portType(OaPortType.OA)
            .subrack(getSubrack())
            .slot(getSlot().getValue());
    }
}
