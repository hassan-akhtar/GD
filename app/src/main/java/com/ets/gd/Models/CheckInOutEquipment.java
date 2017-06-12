package com.ets.gd.Models;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/12/2017.
 * General Data
 */

public class CheckInOutEquipment extends RealmObject {

    private int equipmentID;

    public CheckInOutEquipment(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }
}
