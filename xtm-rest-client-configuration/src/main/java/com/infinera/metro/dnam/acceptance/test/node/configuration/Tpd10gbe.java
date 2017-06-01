package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardType;
import com.infinera.metro.dnam.acceptance.test.node.mib.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.ModuleType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Value
@Builder
public class Tpd10gbe implements Board {
    private final ModuleType moduleType = ModuleType.eq;
    private final GroupOrTableType groupOrTableType = GroupOrTableType.BOARD;
    private final BoardType boardType = BoardType.tpd10gbe;
    @NonNull private final Integer subrack; //These values could be taken from objects "Subrack" and "Slot"?
    @NonNull private final Integer slot;
    @NonNull private final List<Port> clientPorts;
    @NonNull private final List<Port> linePorts;

    public void tpd10gbeSpecificMethod() {
        log.info("Executing tpd10gbe specific method");
    }

    @Override
    public BoardEntry getBoardEntry() {
        return null;
    }

}
