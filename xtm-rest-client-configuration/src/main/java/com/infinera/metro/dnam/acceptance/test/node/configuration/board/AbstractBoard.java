package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardAttributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.PUBLIC)
@EqualsAndHashCode
abstract class AbstractBoard implements Board {
    @JsonIgnore private static final ModuleType MODULE_TYPE = ModuleType.EQ;
    @JsonIgnore private static final GroupOrTableType groupOrTableType = GroupOrTableType.BOARD;
    @NonNull @JsonIgnore private final BoardType boardType;
    @NonNull private final Subrack subrack;
    @NonNull private final Slot slot;
    @NonNull private final List<BoardAttributes> boardAttributes;


    @JsonIgnore
    public BoardEntry getBoardEntry() {
        return BoardEntry.builder()
            .subrack(subrack.getValue())
            .slot(slot.getValue())
            .boardType(boardType)
            .build();
    }

    @Override
    public void applyTo(Node node) throws RuntimeException {
        node.createBoard(getBoardEntry());
        boardAttributes.forEach(boardSetAttribute -> boardSetAttribute.applyTo(node, getBoardEntry()));
    }

    @Override
    public void deleteFrom(Node node) {
        node.deleteBoard(getBoardEntry());
    }
}
