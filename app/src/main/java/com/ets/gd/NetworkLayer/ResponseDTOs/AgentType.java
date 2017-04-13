package com.ets.gd.NetworkLayer.ResponseDTOs;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class AgentType {

    private int ID;
    private String Code;
    private String Description;
    private String ModifiedBy;
    private String ModifiedTime;
    private boolean IsFiveYear;
    private boolean IsSixYear;
    private boolean IsTenYear;
    private boolean  IsTwelveYear;
    private String DueDates;

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

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getModifiedTime() {
        return ModifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        ModifiedTime = modifiedTime;
    }

    public boolean isFiveYear() {
        return IsFiveYear;
    }

    public void setFiveYear(boolean fiveYear) {
        IsFiveYear = fiveYear;
    }

    public boolean isSixYear() {
        return IsSixYear;
    }

    public void setSixYear(boolean sixYear) {
        IsSixYear = sixYear;
    }

    public boolean isTenYear() {
        return IsTenYear;
    }

    public void setTenYear(boolean tenYear) {
        IsTenYear = tenYear;
    }

    public boolean isTwelveYear() {
        return IsTwelveYear;
    }

    public void setTwelveYear(boolean twelveYear) {
        IsTwelveYear = twelveYear;
    }

    public String getDueDates() {
        return DueDates;
    }

    public void setDueDates(String dueDates) {
        DueDates = dueDates;
    }
}
