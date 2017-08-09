package com.ets.gd.Models;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/12/2017.
 * General Data
 */

public class CheckInOutEquipment extends RealmObject {

    private String equipmentCode;

    public CheckInOutEquipment() {
    }

    public CheckInOutEquipment(String equipmentID) {
        this.equipmentCode = equipmentID;
    }

    public String getEquipmentID() {
        return equipmentCode;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentCode = equipmentID;
    }
}
