package com.ets.gd.NetworkLayer.Service;


import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;

public interface MyCallBack {

    void onSuccess(ResponseDTO responseDTO);

    void onFailure(ResponseDTO errorDTO);


}
