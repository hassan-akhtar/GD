package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 2/14/2017.
 * General Data
 */

public class LoginDTO extends BaseDTO {

    private String username;
    private String password;
    private String grantType;


    public LoginDTO(int callBackId, String username, String password, String grantType) {
        super(callBackId);
        this.username = username;
        this.password = password;
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
