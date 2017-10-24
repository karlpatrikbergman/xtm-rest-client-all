package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.Optional;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@Builder
@Value
public class NodeEquipment {
    @Singular  private final List<Board> boards;

    public Optional<Board>getBoard(Subrack subrack, Slot slot) {
        return boards.stream()
            .filter(board -> board.getSubrack().equals(subrack) && board.getSlot().equals(slot))
            .findFirst();
    }

    public void applyTo(Node node) {
        assert node != null;
        boards.forEach(board -> board.applyTo(node));
    }

    public void deleteBoards(Node node) {
        boards.forEach(board-> board.deleteFrom(node));
    }
}
