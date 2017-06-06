package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation;

import java.util.List;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class SyncPostAddETSLocationRequestDTO extends BaseDTO {

    private String userId;
    private List<ETSLocation> lstAddLocation;

    public SyncPostAddETSLocationRequestDTO(int callBackId, String userId, List<ETSLocation> lstAddLocation) {
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

    public List<ETSLocation> getLstAddLocation() {
        return lstAddLocation;
    }

    public void setLstAddLocation(List<ETSLocation> lstAddLocation) {
        this.lstAddLocation = lstAddLocation;
    }
}
