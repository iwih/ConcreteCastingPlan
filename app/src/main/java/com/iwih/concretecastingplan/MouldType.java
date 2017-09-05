package com.iwih.concretecastingplan;

/**
 * Created by iwih on 05/09/2017.
 */

public class MouldType {
    private long _id;
    private String mouldName;
    private long mouldSize;

    public MouldType(long _id, String mouldName, long mouldSize) {
        this._id = _id;
        this.mouldName = mouldName;
        this.mouldSize = mouldSize;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getMouldName() {
        return mouldName;
    }

    public void setMouldName(String mouldName) {
        this.mouldName = mouldName;
    }

    public long getMouldSize() {
        return mouldSize;
    }

    public void setMouldSize(long mouldSize) {
        this.mouldSize = mouldSize;
    }
}
