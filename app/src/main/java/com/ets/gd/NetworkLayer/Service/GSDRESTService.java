package com.ets.gd.NetworkLayer.Service;


import com.ets.gd.Constants.Constants;
import com.ets.gd.NetworkLayer.ResponseDTOs.LoginResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncGetResponseDTO;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

public interface GSDRESTService {


    // Login User Request FTE
    @FormUrlEncoded
    @POST(Constants.URL_LOGIN)
    public void loginRequest(@Field("username") String username,
                             @Field("password") String password,
                             @Field("grant_type") String grantType,
                             Callback<LoginResponseDTO> loginResponseDTOCallback);


    // Sync get data request
    @FormUrlEncoded
    @POST(Constants.URL_SYNC_GET)
    public void getSyncData(@Field("CustomerId") int customerId,
                            @Field("DeviceId") String deviceId,
                            Callback<SyncGetResponseDTO> syncGetResponseDTOCallback);


}

