package com.moez.QKSMS.antispam;

/**
 * Created by dato0 on 3/30/2017.
 */

public enum KeywordType {
    WORD(0), REGEX(1);

    private int numVal;

    KeywordType(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
