package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.PortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OaPortEntry extends AbstractPortEntry implements MibEntry {

    @Builder
    private OaPortEntry(ModuleType moduleType, GroupOrTableType groupOrTableType, PortType portType, Integer subrack, Integer slot, Integer transmitPort, Integer receivePort) {
        super(moduleType, groupOrTableType, portType, subrack, slot, transmitPort, receivePort);
    }

    @Override
    public String getMibEntryString() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (getPortType().getValue(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
    }

    @Override
    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(getModuleType(), getGroupOrTableType(), this);
    }
}
