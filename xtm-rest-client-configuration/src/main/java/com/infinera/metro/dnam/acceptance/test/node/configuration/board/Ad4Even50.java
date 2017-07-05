package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Ad4Even50 extends AbstractBoard implements Board {

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardEntryAttributes"})
    private Ad4Even50(Integer subrack, Slot slot, @Singular List<MibEntryAttributes> boardEntryAttributes) {
        super(BoardType.AD4EVEN50, subrack, slot, boardEntryAttributes);
    }

//    @Override
//    public void applyTo(Node node) {
//        super.createBoard(node);
//        super.configureBoardAttributes(node);
//    }
//
//    @Override
//    public void deleteFrom(Node node) {
//        super.deleteBoard(node);
//    }

}
