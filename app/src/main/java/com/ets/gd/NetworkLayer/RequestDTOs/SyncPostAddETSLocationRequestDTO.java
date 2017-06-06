package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation;

import java.util.List;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class SyncPostAddETSLocationRequestDTO extends BaseDTO {

    private String userId;
    private List<ETSLoc> lstAddLocation;

    public SyncPostAddETSLocationRequestDTO(int callBackId, String userId, List<ETSLoc> lstAddLocation) {
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

    public List<ETSLoc> getLstAddLocation() {
        return lstAddLocation;
    }

    public void setLstAddLocation(List<ETSLoc> lstAddLocation) {
        this.lstAddLocation = lstAddLocation;
    }
}
