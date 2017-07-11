package com.ets.gd.NetworkLayer.ResponseDTOs;

import com.ets.gd.Models.RealmString;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MobileUser extends RealmObject {

    @PrimaryKey
    private int ID;
    private String UserName;
    private String FirstName;
    private String LastName;
    private String Password;
    private String CustomerId;
    private String UserProfileId;
    private RealmList<PermissionType> RolePermissions;

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

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
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

    public RealmList<PermissionType> getRolePermissions() {
        return RolePermissions;
    }

    public void setRolePermissions(RealmList<PermissionType> rolePermissions) {
        RolePermissions = rolePermissions;
    }
}
