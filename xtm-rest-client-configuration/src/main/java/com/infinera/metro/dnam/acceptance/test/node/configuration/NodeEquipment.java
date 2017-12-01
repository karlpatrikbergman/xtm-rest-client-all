package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@Builder
public class NodeEquipment {
    @Singular
    private final List<Board> boards;

    public Board getBoard(Subrack subrack, Slot slot) {
        return boards.stream()
            .filter(board -> board.getSubrack().equals(subrack) && board.getSlot().equals(slot))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Failed to find board in subrack "
                .concat(subrack.name())
                .concat("slot ")
                .concat(slot.name())));
    }

    public <T> T getBoard(Subrack subrack, Slot slot, Class<T> clazz) {
        return clazz.cast(getBoard(subrack, slot));
    }

    public void applyTo(Node node) {
        Preconditions.checkNotNull(node, "Node cannot be null");
        boards.forEach(board -> board.applyTo(node));
    }

    public void deleteFrom(Node node) {
        Preconditions.checkNotNull(node, "Node cannot be null");
        boards.forEach(board -> board.deleteFrom(node));
    }

    public List<Board> getBoards() {
        return this.boards;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof NodeEquipment)) return false;
        final NodeEquipment other = (NodeEquipment) o;
        final Object this$boards = this.getBoards();
        final Object other$boards = other.getBoards();
        return this$boards == null ? other$boards == null : this$boards.equals(other$boards);
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $boards = this.getBoards();
        result = result * PRIME + ($boards == null ? 43 : $boards.hashCode());
        return result;
    }

    public String toString() {
        return "NodeEquipment(boards=" + this.getBoards() + ")";
    }
}
