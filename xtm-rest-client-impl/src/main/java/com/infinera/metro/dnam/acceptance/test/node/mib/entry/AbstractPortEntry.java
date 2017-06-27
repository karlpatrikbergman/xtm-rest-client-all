package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.MibType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Getter;
import lombok.NonNull;

class AbstractPortEntry extends AbstractMibEntry implements MibEntry {
    @Getter @NonNull private final Integer transmitPort;
    @Getter @NonNull private final Integer receivePort;

    AbstractPortEntry(ModuleType moduleType, GroupOrTableType groupOrTableType, MibType mibType, Integer subrack, Integer slot, Integer transmitPort, Integer receivePort) {
        super(moduleType, groupOrTableType, mibType, subrack, slot);
        this.transmitPort = transmitPort;
        this.receivePort = receivePort;
    }

    @Override
    public String getMibEntryString() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (getMibType().getValue(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
    }
}
