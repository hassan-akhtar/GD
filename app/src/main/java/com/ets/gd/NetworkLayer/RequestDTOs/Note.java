package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 4/24/2017.
 * General Data
 */

public class Note {

    private String Note;
    private String ModifiedBy;
    private String ModifiedTime;


    public Note() {
    }

    public Note(String note, String modifiedBy, String modifiedTime) {
        Note = note;
        ModifiedBy = modifiedBy;
        ModifiedTime = modifiedTime;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
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
}
