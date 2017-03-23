package com.ets.gd.Models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hakhtar on 3/23/2017.
 * General Data
 */

public class AssetList implements Serializable {

    private List<Asset> assetList = new ArrayList<Asset>();

    public List<Asset> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<Asset> assetList) {
        this.assetList = assetList;
    }
}
