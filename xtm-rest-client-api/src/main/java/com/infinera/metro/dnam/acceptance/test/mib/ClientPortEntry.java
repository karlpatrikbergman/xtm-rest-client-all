package com.infinera.metro.dnam.acceptance.test.mib;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static com.infinera.metro.dnam.acceptance.test.mib.util.MibPathUtil.MIB_PATH_UTIL;

@Value
@Builder
public class ClientPortEntry implements MibEntry {
    @NonNull private final Module module = Module.CLIENT;
    @NonNull private final GroupOrTable groupOrTable = GroupOrTable.IF;
    @NonNull private final ClientPort clientPort; //Always ClientPort.CLIENT?
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;
    @NonNull private final Integer transmitterPort;
    @NonNull private final Integer receiverPort;

    public String getMibEntryString() {
        return MIB_PATH_UTIL.getMibEntryString (clientPort.getName(), getSubrack() ,getSlot(), this.getTransmitterPort(), getReceiverPort());
    }

    public String getMibEntryPath() {
        return MIB_PATH_UTIL.getMibEntryPath(module, groupOrTable, this);
    }
}

