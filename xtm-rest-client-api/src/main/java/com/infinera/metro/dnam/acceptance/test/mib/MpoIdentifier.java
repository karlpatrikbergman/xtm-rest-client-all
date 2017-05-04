package com.infinera.metro.dnam.acceptance.test.mib;

import com.google.common.base.Preconditions;

/**
 * TODO: Should the numerical value of MPO idendifier be named "port"?
 */
public class MpoIdentifier {
    private final boolean xtmVersionEqualOrHigherThan27;
    private final int mpoPort;
    public static final String MODULE_NOT_PRESENT = ":0";

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

    public static MpoIdentifier createBelowXtmVersion27() {
        return new MpoIdentifier();
    }

    public static MpoIdentifier createMpoIdentifierModuleNotPresent() {
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
