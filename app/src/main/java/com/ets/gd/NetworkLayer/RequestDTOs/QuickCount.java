package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/9/2017.
 * General Data
 */

public class QuickCount extends RealmObject {


    private  int ID;
    private String countType;
    private  String AssetCode;
    private  boolean IsComplete;
    private  String Status;
    private boolean isChanged;
    private  int CustomerID ;
    private int UserId;
    private String CountingDate;
    private  String AssetType;
    private RealmList<QuickCountAsset> QuickCountAssets;

    public QuickCount(){

    }

    public QuickCount(int ID, String assetCode, boolean isComplete, String status, String assetType, RealmList<QuickCountAsset> quickCountAssets,boolean isChanged) {
        this.ID = ID;
        AssetCode = assetCode;
        IsComplete = isComplete;
        Status = status;
        AssetType = assetType;
        QuickCountAssets = quickCountAssets;
        this.isChanged = isChanged;
    }


    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getQuickCountDate() {
        return CountingDate;
    }

    public void setQuickCountDate(String quickCountDate) {
        CountingDate = quickCountDate;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
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

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public void setAssetCode(String assetCode) {
        AssetCode = assetCode;
    }

    public boolean isComplete() {
        return IsComplete;
    }

    public void setComplete(boolean complete) {
        IsComplete = complete;
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

    public RealmList<QuickCountAsset> getQuickCountAssets() {
        return QuickCountAssets;
    }

    public void setQuickCountAssets(RealmList<QuickCountAsset> quickCountAssets) {
        QuickCountAssets = quickCountAssets;
    }
}
