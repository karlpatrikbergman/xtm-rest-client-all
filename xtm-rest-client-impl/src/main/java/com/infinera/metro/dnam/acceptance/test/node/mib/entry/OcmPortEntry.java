package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.OcmPortType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OcmPortEntry extends AbstractPortEntry implements MibEntry {

    @Builder
    private OcmPortEntry(ModuleType moduleType, GroupOrTableType groupOrTableType, OcmPortType ocmPortType, Integer subrack, Integer slot, Integer transmitPort, Integer receivePort) {
        super(moduleType, groupOrTableType, ocmPortType, subrack, slot, transmitPort, receivePort);
    }
}
