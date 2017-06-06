package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class SyncPostToolhawkEquipment extends BaseDTO {

    private String userId;
    private List<THEquipment> lstAddEquipment;
    private List<THEquipment> lstEditEquipment;


    public SyncPostToolhawkEquipment(int callBackId, String userId, List<THEquipment> lstAddEquipment, List<THEquipment> lstEditEquipment) {
        super(callBackId);
        this.userId = userId;
        this.lstAddEquipment = lstAddEquipment;
        this.lstEditEquipment = lstEditEquipment;
    }

    public SyncPostToolhawkEquipment( ) {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<THEquipment> getLstAddEquipment() {
        return lstAddEquipment;
    }

    public void setLstAddEquipment(List<THEquipment> lstAddEquipment) {
        this.lstAddEquipment = lstAddEquipment;
    }

    public List<THEquipment> getLstEditEquipment() {
        return lstEditEquipment;
    }

    public void setLstEditEquipment(List<THEquipment> lstEditEquipment) {
        this.lstEditEquipment = lstEditEquipment;
    }
}
