package com.ets.gd.Interfaces;

import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;

/**
 * Created by hakhtar on 8/5/2017.
 * General Data
 */

public interface EquipmentQuickCountCompleted {

    public void EquipmentQuickCountComplete(ToolhawkEquipment toolhawkEquipment, int pos);
}
