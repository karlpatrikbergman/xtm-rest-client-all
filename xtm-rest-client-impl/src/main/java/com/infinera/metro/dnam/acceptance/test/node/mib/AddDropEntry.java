package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value //Needs jackson > 2.8
@Builder
public class AddDropEntry implements MibEntry {
    @NonNull private final ModuleType moduleType;
    @NonNull private final GroupOrTableType groupOrTableType;
    @NonNull private final ClientPortType clientPortType;
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;

    public String getMibEntryString() {
        assert clientPortType != null;
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (clientPortType.getValue(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
    }

    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(moduleType, groupOrTableType, this);
    }
}
