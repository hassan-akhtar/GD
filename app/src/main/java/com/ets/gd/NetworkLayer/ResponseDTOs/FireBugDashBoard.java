package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 5/23/2017.
 * General Data
 */

public class FireBugDashBoard extends RealmObject {

    InspectionDue inspectionDueCountVM;
    InspectionOverDue inspectionOverDueCount;

    public InspectionDue getInspectionDueCountVM() {
        return inspectionDueCountVM;
    }

    public void setInspectionDueCountVM(InspectionDue inspectionDueCountVM) {
        this.inspectionDueCountVM = inspectionDueCountVM;
    }

    public InspectionOverDue getInspectionOverDueCount() {
        return inspectionOverDueCount;
    }

    public void setInspectionOverDueCount(InspectionOverDue inspectionOverDueCount) {
        this.inspectionOverDueCount = inspectionOverDueCount;
    }
}
