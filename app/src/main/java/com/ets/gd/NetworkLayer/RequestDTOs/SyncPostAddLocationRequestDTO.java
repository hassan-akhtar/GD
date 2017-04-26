package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;



public class SyncPostAddLocationRequestDTO  extends BaseDTO {

    private String userId;
    private List<AddLocation> lstAddLocation;

    public SyncPostAddLocationRequestDTO(int callBackId, String userId, List<AddLocation> lstAddLocation) {
        super(callBackId);
        this.userId = userId;
        this.lstAddLocation = lstAddLocation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AddLocation> getLstAddLocation() {
        return lstAddLocation;
    }

    public void setLstAddLocation(List<AddLocation> lstAddLocation) {
        this.lstAddLocation = lstAddLocation;
    }
}
