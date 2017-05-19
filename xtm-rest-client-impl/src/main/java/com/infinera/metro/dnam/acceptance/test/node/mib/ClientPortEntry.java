package com.infinera.metro.dnam.acceptance.test.node.mib;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate and Jackson
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value //Needs jackson > 2.8
@Builder
public class ClientPortEntry implements MibEntry {
    @NonNull private final Module module = Module.CLIENT;
    @NonNull private final GroupOrTable groupOrTable = GroupOrTable.IF;
    @NonNull private final ClientPort clientPort; //Always ClientPort.CLIENT?
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;

    public String getMibEntryString() {
        assert clientPort != null;
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (clientPort.getName(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
    }

    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(module, groupOrTable, this);
    }
}

