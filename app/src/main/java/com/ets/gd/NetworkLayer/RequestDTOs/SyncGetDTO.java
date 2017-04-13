package com.ets.gd.NetworkLayer.RequestDTOs;

public class SyncGetDTO extends  BaseDTO {

    private int customerId;
    private String deviceId;

    public SyncGetDTO(int callBackId, int customerId, String deviceId) {
        super(callBackId);
        this.customerId = customerId;
        this.deviceId = deviceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
