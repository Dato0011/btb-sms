package com.moez.QKSMS.antispam;

/**
 * Created by dato0 on 3/30/2017.
 */

public class ScoreValue {
    private short id;
    private short value;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public ScoreValue() { ; }

    public ScoreValue(short id, short value) {
        this.id = id;
        this.value = value;
    }
}
