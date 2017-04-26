package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;


public class SyncPostEquipmentRequestDTO extends BaseDTO {

    private String userId;
    private List<Equipment> lstAddEquipment;
    private List<Equipment> lstEditEquipment;

    public SyncPostEquipmentRequestDTO(int callBackId, String userId, List<Equipment> lstAddEquipment, List<Equipment> lstEditEquipment) {
        super(callBackId);
        this.userId = userId;
        this.lstAddEquipment = lstAddEquipment;
        this.lstEditEquipment = lstEditEquipment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Equipment> getLstAddEquipment() {
        return lstAddEquipment;
    }

    public void setLstAddEquipment(List<Equipment> lstAddEquipment) {
        this.lstAddEquipment = lstAddEquipment;
    }

    public List<Equipment> getLstEditEquipment() {
        return lstEditEquipment;
    }

    public void setLstEditEquipment(List<Equipment> lstEditEquipment) {
        this.lstEditEquipment = lstEditEquipment;
    }
}
