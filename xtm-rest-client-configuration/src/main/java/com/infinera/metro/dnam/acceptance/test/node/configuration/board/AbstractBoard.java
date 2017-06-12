package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardType;
import com.infinera.metro.dnam.acceptance.test.node.mib.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.ModuleType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(AccessLevel.PUBLIC)
abstract class AbstractBoard {
    @JsonIgnore private final ModuleType moduleType = ModuleType.EQ;
    @JsonIgnore private final GroupOrTableType groupOrTableType = GroupOrTableType.BOARD;
    @JsonIgnore private final BoardType boardType;
    private final Integer subrack;
    private final Slot slot;

    @JsonIgnore
    public BoardEntry getBoardEntry() {
        return BoardEntry.builder()
            .subrack(subrack)
            .slot(slot.getValue())
            .boardType(boardType)
            .build();
    }

    protected void createBoard(Node node) {
        node.createBoard(getBoardEntry());
    }
}
