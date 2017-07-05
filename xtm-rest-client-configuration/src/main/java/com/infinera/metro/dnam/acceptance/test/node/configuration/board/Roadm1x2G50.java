package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Port;
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
public class Roadm1x2G50 extends AbstractBoard implements Board, ROADM_LINEIF_LINE_LinePortBoard, ROADM_ADDDROPIF_ADDDROP_AddDropPortBoard {
    private final List<Port> addDropPorts;
    private final List<Port> linePorts;

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardEntryAttributes", "addDropPorts", "linePorts"})
    private Roadm1x2G50(Integer subrack, Slot slot, @Singular List<MibEntryAttributes> boardEntryAttributes, @Singular List<Port> addDropPorts, @Singular List<Port> linePorts) {
        super(BoardType.ROADM1X2G50, subrack, slot, boardEntryAttributes);
        this.addDropPorts = addDropPorts;
        this.linePorts = linePorts;
    }

    @Override
    public void applyTo(Node node) {
        super.applyTo(node);
        configureAddDropPorts(node);
        configureLinePorts(node);
    }
}
