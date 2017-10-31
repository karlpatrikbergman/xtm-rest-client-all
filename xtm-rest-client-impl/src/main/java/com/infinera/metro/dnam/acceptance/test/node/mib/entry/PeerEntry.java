package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.PeerType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class PeerEntry extends AbstractMibEntry implements MibEntry {
    private final Integer port;
    private final MpoIdentifier mpoIdentifier;

    @JsonCreator
    @Builder
    private PeerEntry(@JsonProperty("subrack") Integer subrack,
                      @JsonProperty("slot") Integer slot,
                      @NonNull @JsonProperty("port") Integer port,
                      @NonNull @JsonProperty("mpoIdentifier") MpoIdentifier mpoIdentifier) {
        super(ModuleType.TOPO, GroupOrTableType.PEER, PeerType.PEER, subrack, slot);
        this.port = port;
        this.mpoIdentifier = mpoIdentifier;
    }

    @JsonIgnore
    public String getLocalLabel() {
        return getSubrack() + ":" + getSlot() + ":" + getPort();
    }

    @JsonIgnore
    @Override
    public String getMibEntryString() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString(getMibType(), getSubrack(), getSlot(), getPort(), getMpoIdentifier());
    }

    @JsonIgnore
    @Override
    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(getModuleType(), getGroupOrTableType(), this);
    }

}
