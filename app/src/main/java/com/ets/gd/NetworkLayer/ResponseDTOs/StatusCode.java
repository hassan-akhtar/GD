package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class StatusCode extends RealmObject{

    @PrimaryKey
    private int ID;
    private String Code;
    private String Description;
    private String ReadingExtensionID;
    private boolean HasReading;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getReadingExtensionID() {
        return ReadingExtensionID;
    }

    public void setReadingExtensionID(String readingExtensionID) {
        ReadingExtensionID = readingExtensionID;
    }

    public boolean isHasReading() {
        return HasReading;
    }

    public void setHasReading(boolean hasReading) {
        HasReading = hasReading;
    }
}
