package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;


public class SyncPostUnitInspectionRequestDTO extends BaseDTO{

    private String userId;
    private List<UnitinspectionResult> lstInspectionResult;

    public SyncPostUnitInspectionRequestDTO(int callBackId,String userId, List<UnitinspectionResult> lstInspectionResult) {
        setCallBackId(callBackId);
        this.userId = userId;
        this.lstInspectionResult = lstInspectionResult;
    }

    public SyncPostUnitInspectionRequestDTO() {

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UnitinspectionResult> getLstInspectionResult() {
        return lstInspectionResult;
    }

    public void setLstInspectionResult(List<UnitinspectionResult> lstInspectionResult) {
        this.lstInspectionResult = lstInspectionResult;
    }
}
