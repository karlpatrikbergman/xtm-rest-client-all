package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class BoardEntry extends AbstractMibEntry implements MibEntry {

    @JsonCreator
    @Builder
    private BoardEntry(@JsonProperty("boardType") BoardType boardType,
                          @JsonProperty("subrack") Integer subrack,
                          @JsonProperty("slot") Integer slot) {
        super(ModuleType.EQ, GroupOrTableType.BOARD, boardType, subrack, slot);
    }
}
