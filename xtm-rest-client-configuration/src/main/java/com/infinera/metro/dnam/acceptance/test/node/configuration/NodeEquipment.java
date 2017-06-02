package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@Value
@Builder
public class NodeEquipment {
    private final Map<Slot, Board> boards;

    Board getBoard(Slot slot) {
        if(!boards.containsKey(slot)) {
            throw new RuntimeException("No board in slot " + slot);
        }
        return boards.get(slot);
    }

    public void applyTo(Node node) {
        assert node != null;
        boards.entrySet().stream()
        .map(map -> map.getValue())
                .forEach(board -> board.applyTo(node));

    }
}
