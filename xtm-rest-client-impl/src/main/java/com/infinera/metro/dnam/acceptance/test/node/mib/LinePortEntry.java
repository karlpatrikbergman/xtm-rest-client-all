package com.infinera.metro.dnam.acceptance.test.node.mib;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.*;

//TODO: Check which constructors that are acctually needed
//TODO: Check which json annotations that are actually needed
//TODO: ClientPortEntry, LinePortEntry and AddDropEntry are exactly the same. Refactor to remedy code duplication

@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate and Jackson
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value //Needs jackson > 2.8
@Builder
public class LinePortEntry implements MibEntry {
    @NonNull private final ModuleType moduleType;
    @NonNull private final GroupOrTableType groupOrTableType;
    @NonNull private final LinePortType linePortType;
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;

    public String getMibEntryString() {
        assert linePortType != null;
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (linePortType.getValue().toLowerCase(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
    }

    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(moduleType, groupOrTableType, this);
    }
}

