package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 5/23/2017.
 * General Data
 */

public class ToolHawkDashBoard extends RealmObject {

    MaintenanceDue MaintenanceDueCount;

    public MaintenanceDue getMaintenanceDueCount() {
        return MaintenanceDueCount;
    }

    public void setMaintenanceDueCount(MaintenanceDue maintenanceDueCount) {
        MaintenanceDueCount = maintenanceDueCount;
    }
}
