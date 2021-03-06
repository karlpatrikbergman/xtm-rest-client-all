package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.MibType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Getter;
import lombok.NonNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")


public class AbstractPortEntry extends AbstractMibEntry implements MibEntry {
    @Getter
    @NonNull
    private final Integer transmitPort;
    @Getter @NonNull private final Integer receivePort;

    public AbstractPortEntry(
        ModuleType moduleType,
        GroupOrTableType groupOrTableType,
        MibType mibType,
        Integer subrack,
        Integer slot,
        Integer transmitPort,
        Integer receivePort) {
        super(moduleType, groupOrTableType, mibType, subrack, slot);
        this.transmitPort = transmitPort;
        this.receivePort = receivePort;
    }

    @Override
    public String getMibEntryString() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (getMibType().getValue(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
    }
}


//TODO: Do we need @JsonCreator?
//TODO: Used enums Subrack and Slot instead of Integer? If so move these enums to xtm-rest-client-impl or xtm-rest-client-common

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
//@JsonSubTypes({
//    @JsonSubTypes.Type(value = LinePortEntry.class, name = "linePortEntry"),
//    @JsonSubTypes.Type(value = ClientPortEntry.class, name = "clientPortEntry")
//})
//public class AbstractPortEntry extends AbstractMibEntry implements MibEntry {
//    @Getter @NonNull private final Integer transmitPort;
//    @Getter @NonNull private final Integer receivePort;
//
//    @JsonCreator
//    public AbstractPortEntry(
//        @JsonProperty("moduleType") ModuleType moduleType,
//        @JsonProperty("groupOrTableType") GroupOrTableType groupOrTableType,
//        @JsonProperty("mibType") MibType mibType,
//        @JsonProperty("subrack") Integer subrack,
//        @JsonProperty("slot") Integer slot,
//        @JsonProperty("transmitPort") Integer transmitPort,
//        @JsonProperty("receivePort") Integer receivePort) {
//        super(moduleType, groupOrTableType, mibType, subrack, slot);
//        this.transmitPort = transmitPort;
//        this.receivePort = receivePort;
//    }
//
//    @Override
//    public String getMibEntryString() {
//        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (getMibType().getValue(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
//    }
//}
