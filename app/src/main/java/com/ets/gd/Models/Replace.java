package com.ets.gd.Models;

/**
 * Created by hakhtar on 5/16/2017.
 * General Data
 */

public class Replace {

    String message;
    String replaceType;
    int newLocID ;
    String newEqipCode;

    public Replace(String message, String replaceType, int newLocID, String newEqipID) {
        this.message = message;
        this.replaceType = replaceType;
        this.newLocID = newLocID;
        this.newEqipCode = newEqipID;
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

    public String getNewEqipCode() {
        return newEqipCode;
    }

    public void setNewEqipCode(String newEqipCode) {
        this.newEqipCode = newEqipCode;
    }
}
