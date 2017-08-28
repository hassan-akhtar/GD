package com.ets.gd.Models;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 8/28/2017.
 * General Data
 */

public class RouteInspRecord  extends RealmObject{

    int routeAssetID;

    public int getRouteAssetID() {
        return routeAssetID;
    }

    public void setRouteAssetID(int routeAssetID) {
        this.routeAssetID = routeAssetID;
    }

}
