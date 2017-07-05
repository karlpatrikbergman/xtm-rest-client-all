package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * NOTE:
 * Field boardEntryAttributes could be two lists, one for attributes that uses "set.json" and one that uses
 * "configure.json".
 */

@RequiredArgsConstructor
@Getter(AccessLevel.PUBLIC)
abstract class AbstractBoard implements  Board {
    @JsonIgnore private final ModuleType moduleType = ModuleType.EQ;
    @JsonIgnore private final GroupOrTableType groupOrTableType = GroupOrTableType.BOARD;
    @NonNull @JsonIgnore private final BoardType boardType;
    @NonNull private final Integer subrack;
    @NonNull private final Slot slot;
    @NonNull private final List<MibEntryAttributes> boardEntryAttributes;

    @JsonIgnore
    public BoardEntry getBoardEntry() {
        return BoardEntry.builder()
            .subrack(subrack)
            .slot(slot.getValue())
            .boardType(boardType)
            .build();
    }

    @Override
    public void applyTo(Node node) throws RuntimeException {
        node.createBoard(getBoardEntry());
        boardEntryAttributes.forEach(boardSetAttribute -> boardSetAttribute.applyTo(node, getBoardEntry()));
    }

    @Override
    public void deleteFrom(Node node) {
        node.deleteBoard(getBoardEntry());
    }
}