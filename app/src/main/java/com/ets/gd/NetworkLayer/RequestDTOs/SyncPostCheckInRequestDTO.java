package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class SyncPostCheckInRequestDTO extends BaseDTO {

    private int DepartmentID;
    private int returningUserID;
    private int jobNumberID;
    private List<Integer> lstEquipments;

    public SyncPostCheckInRequestDTO() {
    }

    public SyncPostCheckInRequestDTO(int callBackId, int departmentID, int returningUserID, int jobNumberID, List<Integer> lstEquipments) {
        super(callBackId);
        DepartmentID = departmentID;
        this.returningUserID = returningUserID;
        this.jobNumberID = jobNumberID;
        this.lstEquipments = lstEquipments;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
    }

    public int getReturningUserID() {
        return returningUserID;
    }

    public void setReturningUserID(int returningUserID) {
        this.returningUserID = returningUserID;
    }

    public int getJobNumberID() {
        return jobNumberID;
    }

    public void setJobNumberID(int jobNumberID) {
        this.jobNumberID = jobNumberID;
    }

    public List<Integer> getLstEquipments() {
        return lstEquipments;
    }

    public void setLstEquipments(List<Integer> lstEquipments) {
        this.lstEquipments = lstEquipments;
    }
}
