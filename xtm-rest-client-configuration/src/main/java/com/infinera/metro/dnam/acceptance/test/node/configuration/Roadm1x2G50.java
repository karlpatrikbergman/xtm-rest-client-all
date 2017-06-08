package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

//TODO: Refactor board classes to take advantage of code reuse

@Slf4j
@Value
public class Roadm1x2G50 implements Board {
    @Getter(AccessLevel.NONE) private final ModuleType moduleType = ModuleType.EQ;
    @Getter(AccessLevel.NONE) private final GroupOrTableType groupOrTableType = GroupOrTableType.BOARD;
    @JsonIgnore private final BoardType boardType = BoardType.ROADM1X2G50;
    @JsonIgnore @NonNull private final BoardEntry boardEntry;
    @NonNull private final Integer subrack; //These values could be taken from (not yet existing) objects "Subrack" and "Slot"?
    @NonNull private final Slot slot;
    @NonNull private final List<Port> addDropPorts;
    @NonNull private final List<Port> linePorts;
    @JsonIgnore @NonNull private final AddDropEntry.AddDropEntryBuilder addDropEntryBuilder;
    @JsonIgnore private final LinePortEntry.LinePortEntryBuilder linePortEntryBuilder;

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "addDropPorts", "linePorts"})
    private Roadm1x2G50(Integer subrack, Slot slot, List<Port> addDropPorts, List<Port> linePorts) {
        this.subrack = subrack;
        this.slot = slot;
        this.addDropPorts = addDropPorts;
        this.linePorts = linePorts;
        this.boardEntry = getBoardEntry();
        this.addDropEntryBuilder = getAddDropEntryBuilder();
        this.linePortEntryBuilder = getLinePortEntryBuilder();
    }

    @Override
    public void applyTo(Node node) {
        createBoard(node);
    }

    private void createBoard(Node node) {
        node.createBoard(getBoardEntry());
    }

    @Override
    public BoardEntry getBoardEntry() {
        return BoardEntry.builder()
                .subrack(subrack)
                .slot(slot.getValue())
                .boardType(boardType)
                .build();
    }

    public AddDropEntry.AddDropEntryBuilder getAddDropEntryBuilder() {
        return AddDropEntry.builder()
                .moduleType(ModuleType.ROADM)
                .groupOrTableType(GroupOrTableType.ADD_DROP_IF)
                .clientPortType(ClientPortType.ADD_DROP)
                .subrack(this.subrack)
                .slot(this.slot.getValue());
    }

    public LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
                .moduleType(ModuleType.WDM)
                .groupOrTableType(GroupOrTableType.IF)
                .linePortType(LinePortType.WDM)
                .subrack(this.subrack)
                .slot(this.slot.getValue());
    }
}
