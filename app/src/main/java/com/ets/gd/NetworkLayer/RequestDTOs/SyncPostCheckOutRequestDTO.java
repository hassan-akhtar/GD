package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class SyncPostCheckOutRequestDTO extends BaseDTO {

    private int DepartmentID;
    private String CheckoutType;
    private int userID;
    private int jobNumberID;
    private List<Integer> lstEquipments;

    public SyncPostCheckOutRequestDTO() {
    }

    public SyncPostCheckOutRequestDTO(int callBackId, int departmentID, String checkoutType, int userID, int jobNumberID, List<Integer> lstEquipments) {
        super(callBackId);
        DepartmentID = departmentID;
        CheckoutType = checkoutType;
        this.userID = userID;
        this.jobNumberID = jobNumberID;
        this.lstEquipments = lstEquipments;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
    }

    public String getCheckoutType() {
        return CheckoutType;
    }

    public void setCheckoutType(String checkoutType) {
        CheckoutType = checkoutType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
