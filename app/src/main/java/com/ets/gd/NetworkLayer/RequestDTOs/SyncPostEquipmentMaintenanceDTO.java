package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/10/2017.
 * General Data
 */

public class SyncPostEquipmentMaintenanceDTO extends BaseDTO {

    List<EquipmentMaintenance> equipmentMaintenanceList;

    public SyncPostEquipmentMaintenanceDTO(int callBackId, List<EquipmentMaintenance> equipmentMaintenanceList) {
        super(callBackId);
        this.equipmentMaintenanceList = equipmentMaintenanceList;
    }

    public SyncPostEquipmentMaintenanceDTO() {
    }

    public List<EquipmentMaintenance> getEquipmentMaintenanceList() {
        return equipmentMaintenanceList;
    }

    public void setEquipmentMaintenanceList(List<EquipmentMaintenance> equipmentMaintenanceList) {
        this.equipmentMaintenanceList = equipmentMaintenanceList;
    }
}
