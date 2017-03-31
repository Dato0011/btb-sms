package com.moez.QKSMS.antispam;

/**
 * Created by dato0 on 3/30/2017.
 */

public class ScoreCombination {
    private short primary;
    private short secondary;
    private short multiplier;

    public short getPrimary() {
        return primary;
    }

    public void setPrimary(short primary) {
        this.primary = primary;
    }

    public short getSecondary() {
        return secondary;
    }

    public void setSecondary(short secondary) {
        this.secondary = secondary;
    }

    public short getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(short multiplier) {
        this.multiplier = multiplier;
    }

    public ScoreCombination() { ; }

    public ScoreCombination(short primary, short secondary, short multiplier) {
        this.primary = primary;
        this.secondary = secondary;
        this.multiplier = multiplier;
    }
}
