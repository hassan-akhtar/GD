package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;


public class SyncPostUnitInspectionRequestDTO {

    private int userId;
    private List<InspectionResult> lstInspectionResult;

    public SyncPostUnitInspectionRequestDTO(int userId, List<InspectionResult> lstInspectionResult) {
        this.userId = userId;
        this.lstInspectionResult = lstInspectionResult;
    }

    public SyncPostUnitInspectionRequestDTO() {

    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<InspectionResult> getLstInspectionResult() {
        return lstInspectionResult;
    }

    public void setLstInspectionResult(List<InspectionResult> lstInspectionResult) {
        this.lstInspectionResult = lstInspectionResult;
    }
}
