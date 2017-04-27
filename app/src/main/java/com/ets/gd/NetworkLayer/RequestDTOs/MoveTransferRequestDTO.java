package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 4/27/2017.
 * General Data
 */

public class MoveTransferRequestDTO extends BaseDTO {


    private String userId;
    private List<MoveTransfer> lstEquipment;


    public MoveTransferRequestDTO(int callBackId, String userId, List<MoveTransfer> lstEquipment) {
        super(callBackId);
        this.userId = userId;
        this.lstEquipment = lstEquipment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<MoveTransfer> getLstEquipment() {
        return lstEquipment;
    }

    public void setLstEquipment(List<MoveTransfer> lstEquipment) {
        this.lstEquipment = lstEquipment;
    }
}
