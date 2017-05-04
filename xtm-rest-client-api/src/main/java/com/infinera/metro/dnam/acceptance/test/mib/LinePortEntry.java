package com.infinera.metro.dnam.acceptance.test.mib;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static com.infinera.metro.dnam.acceptance.test.mib.util.MibPathUtil.MIB_PATH_UTIL;

@Value
@Builder
public class LinePortEntry implements MibEntry {
    @NonNull private final Module module = Module.WDM;
    @NonNull private final GroupOrTable groupOrTable = GroupOrTable.IF;
    @NonNull private final LinePort linePort; //Always WDM?
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;

    public String getMibEntryString() {
        return MIB_PATH_UTIL.getMibEntryString (linePort.getName(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
    }

    public String getMibEntryPath() {
        return MIB_PATH_UTIL.getMibEntryPath(module, groupOrTable, this);
    }
}

