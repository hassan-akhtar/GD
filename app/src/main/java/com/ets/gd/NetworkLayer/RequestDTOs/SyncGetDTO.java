package com.ets.gd.NetworkLayer.RequestDTOs;

public class SyncGetDTO extends  BaseDTO {


    private String deviceId;

    public SyncGetDTO(int callBackId, String deviceId) {
        super(callBackId);
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
