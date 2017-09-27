package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.InternalConnectionType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.stringtemplate.v4.ST;

import static com.infinera.metro.dnam.acceptance.test.node.mib.util.PortUtil.PORT_UTIL;

/**
 * This class is not equal in structure to other classes implementing MibEntry. One difference is that is
 * does not only have one value for subrack/slot/, as for example PeerEntry. Instead it has values
 * for from-subrack/from-slot/ and also for to-subrack/to-slot/.
 *
 * To take advantage of code reuse InternalConnectionEntry still extends AbstractMibEntry, and the fields have the
 * following meanings:
 *      subrack = from-subrack
 *      slot = from-slot
 *
 * The class is also extended with extra fields toSlot and toSubrack.
 *
 * TODO: Is the interface the same as MpoIdentifier?
 *
 * topo/internal/int:1:2:0:1:1:19:0:3/create.json
 **/
@EqualsAndHashCode(callSuper = true)
@Value
public class InternalConnectionEntry extends AbstractMibEntry implements MibEntry {
    private final Integer port;
    private final MpoIdentifier mpoIdentifier;
    private final Integer toSubrack;
    private final Integer toSlot;
    private final MpoIdentifier toMpoIdentifier;
    private final Integer toPort;

    @JsonCreator
    @Builder
    private InternalConnectionEntry(
        @NonNull @JsonProperty("fromSubrack") Integer fromSubrack,
        @NonNull @JsonProperty("fromSlot") Integer fromSlot,
        @NonNull @JsonProperty("fromPort") Integer fromPort,
        @NonNull @JsonProperty("fromMpoIdentifier") MpoIdentifier fromMpoIdentifier,

        @NonNull @JsonProperty("toSubrack") Integer toSubrack,
        @NonNull @JsonProperty("toSlot") Integer toSlot,
        @NonNull @JsonProperty("toPort") Integer toPort,
        @NonNull @JsonProperty("toMpoIdentifier") MpoIdentifier toMpoIdentifier) {

        super(ModuleType.TOPO, GroupOrTableType.INTERNAL, InternalConnectionType.INT, fromSubrack, fromSlot);
        this.port = fromPort;
        this.mpoIdentifier = fromMpoIdentifier;
        this.toSubrack = toSubrack;
        this.toSlot = toSlot;
        this.toPort = toPort;
        this.toMpoIdentifier = toMpoIdentifier;
    }

    @JsonIgnore
    @Override
    public String getMibEntryString() {
        final ST result = new ST("<entry>:<from_subrack>:<from_slot><from_mtoIdentifier>:<from_port>:<to_subrack>:<to_slot><to_mtoIdentifier>:<to_port>");
        result.add("entry", getMibType().getValue());
        result.add("from_subrack", getSubrack());
        result.add("from_slot", getSlot());
        result.add("from_mtoIdentifier", getMpoIdentifier());
        result.add("from_port", getPort());
        result.add("to_subrack", getToSubrack());
        result.add("to_slot", getToSlot());
        result.add("to_mtoIdentifier", getToMpoIdentifier());
        result.add("to_port", getToPort());
        return result.render();
    }

    @JsonIgnore
    @Override
    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(getModuleType(), getGroupOrTableType(), this);
    }

    public InternalConnectionEntry reverse() {
        return InternalConnectionEntry.builder()
            .fromSubrack(toSubrack)
            .fromSlot(toSlot)
            .fromMpoIdentifier(toMpoIdentifier)
            .fromPort(PORT_UTIL.reversePort(toPort))
            .toSubrack(getSubrack())
            .toSlot(getSlot())
            .toMpoIdentifier(mpoIdentifier)
            .toPort(PORT_UTIL.reversePort(getPort()))
            .build();
    }


}
