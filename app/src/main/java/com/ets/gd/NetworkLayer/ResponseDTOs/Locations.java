package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class Locations  extends RealmObject {

    @PrimaryKey
    private int ID;
    private String Code;
    private String Description;
    private String Floor;
    private String Room;
    private Customer Customer;
    private Site Site;
    private Building Building;
}
