package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class InspectionResult  extends RealmObject {

    @PrimaryKey
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
