package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MobileUser  extends RealmObject {


    @PrimaryKey
    private int ID;
    private String UserName;
    private String Password;
    private String CustomerId;
    private String UserProfileId;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        UserProfileId = userProfileId;
    }
}
