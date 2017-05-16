package com.ets.gd.Models;

/**
 * Created by hakhtar on 5/16/2017.
 * General Data
 */

public class Replace {

    String message;
    String replaceType;
    int newLocID ;
    int newEqipID;

    public Replace(String message, String replaceType, int newLocID, int newEqipID) {
        this.message = message;
        this.replaceType = replaceType;
        this.newLocID = newLocID;
        this.newEqipID = newEqipID;
    }

    public Replace() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReplaceType() {
        return replaceType;
    }

    public void setReplaceType(String replaceType) {
        this.replaceType = replaceType;
    }

    public int getNewLocID() {
        return newLocID;
    }

    public void setNewLocID(int newLocID) {
        this.newLocID = newLocID;
    }

    public int getNewEqipID() {
        return newEqipID;
    }

    public void setNewEqipID(int newEqipID) {
        this.newEqipID = newEqipID;
    }
}
