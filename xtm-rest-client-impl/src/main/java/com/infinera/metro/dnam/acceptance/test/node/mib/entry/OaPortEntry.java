package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.OaPortType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OaPortEntry extends AbstractPortEntry implements MibEntry {

    @Builder
    private OaPortEntry(ModuleType moduleType, GroupOrTableType groupOrTableType, OaPortType oaPortType, Integer subrack, Integer slot, Integer transmitPort, Integer receivePort) {
        super(moduleType, groupOrTableType, oaPortType, subrack, slot, transmitPort, receivePort);
    }
}