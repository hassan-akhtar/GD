package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class Images extends RealmObject{


    @PrimaryKey
    private int ID;
    private int EquipmentID;
    private String Path;
    private String FileName;
    private String AssetType;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        EquipmentID = equipmentID;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getAssetType() {
        return AssetType;
    }

    public void setAssetType(String assetType) {
        AssetType = assetType;
    }
}
