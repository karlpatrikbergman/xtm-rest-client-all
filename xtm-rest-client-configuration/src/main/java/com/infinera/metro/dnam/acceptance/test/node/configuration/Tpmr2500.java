package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
@Builder
public class Tpmr2500 implements Board {
    private final BoardType boardType = BoardType.TPMR2500;
    private String client;
    private String groupOrTable;
    private String module;
    private String tpmr2500SpecificField;

    public void tpmr2500SpecificMethod() {
        log.info("Executing tpmr2500 specific method");
    }

    @Override
    public BoardEntry getBoardEntry() {
        return null;
    }

    @Override
    public void applyTo(Node node) {

    }
}
