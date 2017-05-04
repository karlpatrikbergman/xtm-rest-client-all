package com.infinera.metro.dnam.acceptance.test.mib;

import com.google.common.base.Preconditions;

/**
 * TODO: Should the numerical value of MTO idendifier be named "port"?
 */
public class MtoIdentifier {
    private boolean xtmVersionEqualOrHigherThan27;
    private int mtoPort;

    public MtoIdentifier(int mtoPort) {
        Preconditions.checkArgument(mtoPort >= 0, "mtoIdentifier cannot be negative");
        this.mtoPort = mtoPort;
        this.xtmVersionEqualOrHigherThan27 = true;
    }

    private MtoIdentifier() {
        xtmVersionEqualOrHigherThan27 = false;
    }

    public boolean isXtmVersionEqualOrHigherThan27() {
        return xtmVersionEqualOrHigherThan27;
    }

    public static MtoIdentifier createbelowXtmVersion27() {
        return new MtoIdentifier();
    }

    public String getMtoIdentifier() {
        return (isXtmVersionEqualOrHigherThan27()) ? ":" + mtoPort : "";
    }

    @Override
    public String toString() {
        return getMtoIdentifier();
    }
}
