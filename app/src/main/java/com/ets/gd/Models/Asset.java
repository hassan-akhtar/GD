package com.ets.gd.Models;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Asset extends RealmObject implements Serializable {

    private String tagID;
    private String deviceType;
    private String manufacturers;
    private String model;
    private String serialNo;
    private String mfgDate;
    private String vendor;
    private String Agent;
    private Location location;
    private RealmList<Note> noteList;
    private InspectionDates inspectionDates;

    public Asset(String tagID, String deviceType, String manufacturers, String model, String serialNo, String mfgDate, String vendor, String agent) {
        this.tagID = tagID;
        this.deviceType = deviceType;
        this.manufacturers = manufacturers;
        this.model = model;
        this.serialNo = serialNo;
        this.mfgDate = mfgDate;
        this.vendor = vendor;
        Agent = agent;

    }

    public Asset(Location location) {
        this.location = location;
    }


    public Asset(InspectionDates inspectionDates) {
        this.inspectionDates = inspectionDates;
    }

    public Asset() {
    }

    public String getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(String mfgDate) {
        this.mfgDate = mfgDate;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String manufacturers) {
        this.manufacturers = manufacturers;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RealmList<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(RealmList<Note> noteList) {
        this.noteList = noteList;
    }

    public InspectionDates getInspectionDates() {
        return inspectionDates;
    }

    public void setInspectionDates(InspectionDates inspectionDates) {
        this.inspectionDates = inspectionDates;
    }
}
