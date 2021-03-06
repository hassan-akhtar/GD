package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/2/2017.
 * General Data
 */

public class TransferToolhawkEquipment {

    private String userId;
    private List<ToolhawkTransferDTO> lstEquipment;


    public TransferToolhawkEquipment( ) {
    }

    public TransferToolhawkEquipment(String userId, List<ToolhawkTransferDTO> lstEquipment) {
        this.userId = userId;
        this.lstEquipment = lstEquipment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ToolhawkTransferDTO> getLstEquipment() {
        return lstEquipment;
    }

    public void setLstEquipment(List<ToolhawkTransferDTO> lstEquipment) {
        this.lstEquipment = lstEquipment;
    }
}
