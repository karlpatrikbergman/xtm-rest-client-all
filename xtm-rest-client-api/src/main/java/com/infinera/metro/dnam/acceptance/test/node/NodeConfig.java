package com.infinera.metro.dnam.acceptance.test.node;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.mib.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.mib.LinePortEntry;

import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

/**
 * NOTE: Uses a custom build() method, therfore not lombok.Builder
 */

@Value
public class NodeConfig {
    @NonNull Node node;
    @NonNull BoardEntry boardEntry;
    @NonNull LinePortEntry linePortEntry;
    @NonNull Configuration linePortConfiguration;
    @NonNull ClientPortEntry clientPortEntry;
    @NonNull Configuration clientPortConfiguration;

    @java.beans.ConstructorProperties({"node", "boardEntry", "linePortEntry", "linePortConfiguration", "clientPortEntry", "clientPortConfiguration"})
    NodeConfig(Node node, BoardEntry boardEntry, LinePortEntry linePortEntry, Configuration linePortConfiguration, ClientPortEntry clientPortEntry, Configuration clientPortConfiguration) {
        this.node = node;
        this.boardEntry = boardEntry;
        this.linePortEntry = linePortEntry;
        this.linePortConfiguration = linePortConfiguration;
        this.clientPortEntry = clientPortEntry;
        this.clientPortConfiguration = clientPortConfiguration;
    }

    public static NodeConfigBuilder builder() {
        return new NodeConfigBuilder();
    }

    public static class NodeConfigBuilder {
        private Node node;
        private BoardEntry boardEntry;
        private LinePortEntry linePortEntry;
        private Configuration linePortConfiguration;
        private ClientPortEntry clientPortEntry;
        private Configuration clientPortConfiguration;

        NodeConfigBuilder() {
        }

        public NodeConfig.NodeConfigBuilder node(Node node) {
            this.node = node;
            return this;
        }

        public NodeConfig.NodeConfigBuilder boardEntry(BoardEntry boardEntry) {
            this.boardEntry = boardEntry;
            return this;
        }

        public NodeConfig.NodeConfigBuilder linePortEntry(LinePortEntry linePortEntry) {
            this.linePortEntry = linePortEntry;
            return this;
        }

        public NodeConfig.NodeConfigBuilder linePortConfiguration(Configuration linePortConfiguration) {
            this.linePortConfiguration = linePortConfiguration;
            return this;
        }

        public NodeConfig.NodeConfigBuilder clientPortEntry(ClientPortEntry clientPortEntry) {
            this.clientPortEntry = clientPortEntry;
            return this;
        }

        public NodeConfig.NodeConfigBuilder clientPortConfiguration(Configuration clientPortConfiguration) {
            this.clientPortConfiguration = clientPortConfiguration;
            return this;
        }

        public NodeConfig build() {
            List<Integer> subRackSettings = Arrays.asList(boardEntry.getSubrack(), linePortEntry.getSubrack(), clientPortEntry.getSubrack());
            boolean b = settingsAreEqual(subRackSettings);
            Preconditions.checkState(settingsAreEqual(subRackSettings), "BoardEntry, LineEntry and ClientEntry must have the same subrack setting");

            List<Integer> slotSettings = Arrays.asList(boardEntry.getSlot(), linePortEntry.getSlot(), clientPortEntry.getSlot());
            Preconditions.checkState(settingsAreEqual(slotSettings), "BoardEntry, LineEntry and ClientEntry must have the same slot setting");
            return new NodeConfig(node, boardEntry, linePortEntry, linePortConfiguration, clientPortEntry, clientPortConfiguration);
        }

        private boolean settingsAreEqual(List<Integer> settings) {
            return settings.stream().distinct().limit(2).count() <= 1;
        }

        public String toString() {
            return "com.infinera.metro.dnam.acceptance.test.node.NodeConfig.NodeConfigBuilder(node=" + this.node + ", boardEntry=" + this.boardEntry + ", linePortEntry=" + this.linePortEntry + ", linePortConfiguration=" + this.linePortConfiguration + ", clientPortEntry=" + this.clientPortEntry + ", clientPortConfiguration=" + this.clientPortConfiguration + ")";
        }
    }
}
