package com.ets.gd.NetworkLayer.Service;


import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;

/**
 * Created by hakhtar on 5/4/2016.
 */
public interface MyCallBack {

    void onSuccess(ResponseDTO responseDTO);

    void onFailure(ResponseDTO errorDTO);


}
