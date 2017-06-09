package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/9/2017.
 * General Data
 */

public class QuickCount extends RealmObject {


    private  int ID;
    private  String AssetCode;
    private  int IsComplete;
    private  String Status;
    private  String AssetType;
    private List<QuickCountAsset> QuickCountAssets;

    QuickCount(){

    }

    public QuickCount(int ID, String assetCode, int isComplete, String status, String assetType, List<QuickCountAsset> quickCountAssets) {
        this.ID = ID;
        AssetCode = assetCode;
        IsComplete = isComplete;
        Status = status;
        AssetType = assetType;
        QuickCountAssets = quickCountAssets;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAssetCode() {
        return AssetCode;
    }

    public void setAssetCode(String assetCode) {
        AssetCode = assetCode;
    }

    public int getIsComplete() {
        return IsComplete;
    }

    public void setIsComplete(int isComplete) {
        IsComplete = isComplete;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAssetType() {
        return AssetType;
    }

    public void setAssetType(String assetType) {
        AssetType = assetType;
    }

    public List<QuickCountAsset> getQuickCountAssets() {
        return QuickCountAssets;
    }

    public void setQuickCountAssets(List<QuickCountAsset> quickCountAssets) {
        QuickCountAssets = quickCountAssets;
    }
}
