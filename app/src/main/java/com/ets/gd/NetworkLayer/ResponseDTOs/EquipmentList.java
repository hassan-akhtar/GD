package com.ets.gd.NetworkLayer.ResponseDTOs;

import com.ets.gd.Models.Asset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hakhtar on 4/17/2017.
 * General Data
 */

public class EquipmentList implements Serializable {

    private List<FireBugEquipment> assetList = new ArrayList<FireBugEquipment>();

    public List<FireBugEquipment> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<FireBugEquipment> assetList) {
        this.assetList = assetList;
    }
}