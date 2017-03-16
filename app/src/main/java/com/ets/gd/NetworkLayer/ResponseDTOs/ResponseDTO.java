package com.ets.gd.NetworkLayer.ResponseDTOs;


import com.ets.gd.NetworkLayer.RequestDTOs.BaseDTO;

/**
 * Created by hakhtar on 5/4/2016.
 */
public class ResponseDTO extends BaseDTO {

    public static final String SUCCESS = "000";
    private String status;
    private String Message;
    private int code;


    public ResponseDTO() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResponseDTO(String message) {
        this.Message = message;
    }


    public ResponseDTO(String message, int code) {
        this.Message = message;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public boolean isSuccess() {
        return SUCCESS.equals(this.getCode());
    }

}

