package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;

/**
 * TODO: Should the numerical value of MPO idendifier be named "port"?
 * TODO: Fix this class, it is ugly
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class MpoIdentifier {
    private final boolean xtmVersionEqualOrHigherThan27;
    private final int mpoPort;

    public MpoIdentifier(int mpoPort) {
        Preconditions.checkArgument(mpoPort >= 0, "mtoIdentifier cannot be negative");
        this.mpoPort = mpoPort;
        this.xtmVersionEqualOrHigherThan27 = true;
    }

    private MpoIdentifier() {
        this.mpoPort = 0;
        this.xtmVersionEqualOrHigherThan27 = false;
    }

    public boolean isXtmVersionEqualOrHigherThan27() {
        return xtmVersionEqualOrHigherThan27;
    }

    public static MpoIdentifier belowXtmVersion27() {
        return new MpoIdentifier();
    }

    public static MpoIdentifier NotPresent() {
        return new MpoIdentifier(0);
    }

    public String getMtoIdentifier() {
        return (isXtmVersionEqualOrHigherThan27()) ? ":" + mpoPort : "";
    }

    @Override
    public String toString() {
        return getMtoIdentifier();
    }
}
