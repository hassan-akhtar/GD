package com.ets.gd.NetworkLayer.ResponseDTOs;


public class RegisteredDevice {

    private int ID;
    private String DeviceID;
    private String SerialNumber;
    private String RegistrationKey;
    private String RegistrationDate;
    private String ModifiedBy;
    private String ModifiedTime;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getRegistrationKey() {
        return RegistrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        RegistrationKey = registrationKey;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        RegistrationDate = registrationDate;
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
