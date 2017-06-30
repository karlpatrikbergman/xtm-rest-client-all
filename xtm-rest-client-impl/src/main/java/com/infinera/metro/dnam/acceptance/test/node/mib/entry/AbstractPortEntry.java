package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.MibType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Getter;
import lombok.NonNull;

//TODO: Do we need @JsonCreator?

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LinePortEntry.class, name = "linePortEntry")
})
public class AbstractPortEntry extends AbstractMibEntry implements MibEntry {
    @Getter @NonNull private final Integer transmitPort;
    @Getter @NonNull private final Integer receivePort;

    @JsonCreator
    public AbstractPortEntry(
        @JsonProperty("moduleType") ModuleType moduleType,
        @JsonProperty("groupOrTableType") GroupOrTableType groupOrTableType,
        @JsonProperty("mibType") MibType mibType,
        @JsonProperty("subrack") Integer subrack,
        @JsonProperty("slot") Integer slot,
        @JsonProperty("transmitPort") Integer transmitPort,
        @JsonProperty("receivePort") Integer receivePort) {
        super(moduleType, groupOrTableType, mibType, subrack, slot);
        this.transmitPort = transmitPort;
        this.receivePort = receivePort;
    }

    @Override
    public String getMibEntryString() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (getMibType().getValue(), getSubrack() ,getSlot(), this.getTransmitPort(), this.getReceivePort());
    }
}
