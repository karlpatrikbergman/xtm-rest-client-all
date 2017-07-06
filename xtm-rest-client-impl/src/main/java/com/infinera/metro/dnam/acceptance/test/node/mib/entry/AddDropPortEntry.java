package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

//TODO: This class is equal to ClientPortEntry. Code reuse some how?

@EqualsAndHashCode(callSuper = true)
@Value
public class AddDropPortEntry extends AbstractPortEntry implements MibEntry {
    @Builder
    private AddDropPortEntry(ModuleType moduleType, GroupOrTableType groupOrTableType, ClientPortType clientPortType, Integer subrack, Integer slot, Integer transmitPort, Integer receivePort) {
        super(moduleType, groupOrTableType, clientPortType, subrack, slot, transmitPort, receivePort);
    }
}
