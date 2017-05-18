package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.IOException;

@Builder
@Value
public class NodeConfiguration {
    @NonNull Node node;
    @NonNull NodeEquipment nodeEquipment;

    public void apply() throws IOException {
        final BoardEntry boardEntry = nodeEquipment.getBoardEntry();
        node.createBoard(boardEntry);

        final LinePortEntry linePortEntry = nodeEquipment.getLinePortEntry();
        final Configuration linePortConfiguration = nodeEquipment.getLinePortConfiguration();
        node.setLinePortConfiguration(linePortEntry, ParameterList.of(linePortConfiguration));

        final ClientPortEntry clientPortEntry = nodeEquipment.getClientPortEntry();
        final Configuration clientPortConfiguration = nodeEquipment.getClientPortConfiguration();
        node.setClientPortConfiguration(clientPortEntry, ParameterList.of(clientPortConfiguration));
    }
}
