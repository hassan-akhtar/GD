package com.ets.gd.NetworkLayer.ResponseDTOs;

import java.util.List;

/**
 * Created by hakhtar on 4/26/2017.
 * General Data
 */

public class SyncPostEquipmentResponseDTO  extends ResponseDTO{

    List<SyncPostEquipment> syncPostEquipments;

    public List<SyncPostEquipment> getSyncPostEquipments() {
        return syncPostEquipments;
    }

    public void setSyncPostEquipments(List<SyncPostEquipment> syncPostEquipments) {
        this.syncPostEquipments = syncPostEquipments;
    }
}
