package com.infinera.metro.dnam.acceptance.test.mib;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static com.infinera.metro.dnam.acceptance.test.mib.util.MibPathUtil.MIB_PATH_UTIL;

@Value
@Builder
public class LinePortEntry implements MibEntry {
    @NonNull private final LinePort linePort;
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;
    @NonNull private final Module module = Module.WDM;
    @NonNull private final GroupOrTable groupOrTable = GroupOrTable.IF;
    @NonNull private final Integer transceiverPort;
    @NonNull private final Integer receiverPort;

    public String getMibEntryString() {
        return MIB_PATH_UTIL.getMibEntryString (linePort.getName(), getSubrack() ,getSlot(), getTransceiverPort(), getReceiverPort());
    }

    public String getMibEntryPath() {
        return MIB_PATH_UTIL.getMibEntryPath(module, groupOrTable, this);
    }
}

