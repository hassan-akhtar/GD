package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmObject;

public class QuickCountAsset extends RealmObject {

    private  int ID;
    private  int AssetID;
    private  boolean isFound;
    private  int QuickCountID;
    private  String AssetCode;


    public QuickCountAsset(int ID, int assetID, boolean isFound, int quickCountID, String assetCode) {
        this.ID = ID;
        AssetID = assetID;
        this.isFound = isFound;
        QuickCountID = quickCountID;
        AssetCode = assetCode;
    }

    public QuickCountAsset() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAssetID() {
        return AssetID;
    }

    public void setAssetID(int assetID) {
        AssetID = assetID;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    public int getQuickCountID() {
        return QuickCountID;
    }

    public void setQuickCountID(int quickCountID) {
        QuickCountID = quickCountID;
    }

    public String getAssetCode() {
        return AssetCode;
    }

    public void setAssetCode(String assetCode) {
        AssetCode = assetCode;
    }
}
