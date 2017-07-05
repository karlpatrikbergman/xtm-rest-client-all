package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;
//TODO: Add lombok @Singular

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@Value
@Builder
public class NodeEquipment {
    @Singular private final Map<Slot, Board> boards;

    public Board getBoard(Slot slot) {
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

    public void deleteBoards(Node node) {
        boards.forEach((slot, board) -> board.deleteFrom(node));
    }
}
