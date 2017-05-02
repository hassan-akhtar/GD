package com.ets.gd.NetworkLayer.RequestDTOs;

public class SyncGetDTO extends  BaseDTO {

    private String CustomerCode;
    private String deviceId;

    public SyncGetDTO(int callBackId, String customerId, String deviceId) {
        super(callBackId);
        this.CustomerCode = customerId;
        this.deviceId = deviceId;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.CustomerCode = customerCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
