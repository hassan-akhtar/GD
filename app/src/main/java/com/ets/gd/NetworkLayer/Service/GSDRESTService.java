package com.ets.gd.NetworkLayer.Service;


import com.ets.gd.Constants.Constants;
import com.ets.gd.NetworkLayer.RequestDTOs.ETSLoc;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransferRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostToolhawkEquipment;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostUnitInspectionRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.TransferToolhawk;
import com.ets.gd.NetworkLayer.ResponseDTOs.LoginResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipment;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

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
    public void getSyncData(@Field("DeviceId") String deviceId,
                            Callback<SyncGetResponseDTO> syncGetResponseDTOCallback);

    // Sync post data request equipment
    @POST(Constants.URL_SYNC_POST_EQUIPMENT)
    public void postSyncEquipment(@Body SyncPostEquipmentRequestDTO syncPostRequestDTO,
                                  Callback<List<SyncPostEquipment>> syncGetResponseDTOCallback);


    // Sync post data request location
    @POST(Constants.URL_SYNC_POST_ADD_LOCATION)
    public void postSyncLocation(@Body SyncPostAddLocationRequestDTO syncPostAddLocationRequestDTO,
                                  Callback<List<SyncPostEquipment>> syncGetResponseDTOCallback);




    // Sync post data request move transfer
    @POST(Constants.URL_SYNC_POST_MOVE_TRANSFER)
    public void postMoveTransfer(@Body MoveTransferRequestDTO moveTransferRequestDTO,
                                 Callback<List<SyncPostEquipment>> syncGetResponseDTOCallback);


    // Sync post data request inspect equipment
    @POST(Constants.URL_SYNC_POST_INSPECT_EQUIPMENT)
    public void postInspectEquipment(@Body SyncPostUnitInspectionRequestDTO syncPostUnitInspectionRequestDTO,
                                 Callback<List<SyncPostEquipment>> syncGetResponseDTOCallback);



    // Sync post data request equipment
    @POST(Constants.URL_SYNC_POST_TOOLHAWK_EQUIPMENT)
    public void postSyncToolhawkEquipment(@Body SyncPostToolhawkEquipment syncPostToolhawkEquipment,
                                  Callback<List<SyncPostEquipment>> syncGetResponseDTOCallback);



    // Sync post data request location
    @POST(Constants.URL_SYNC_POST_ETS_LOCATION)
    public void postSyncETSLocation(@Body List<ETSLoc> lstAddLocation,
                                    Callback<List<SyncPostEquipment>> syncGetResponseDTOCallback);


    // Sync post data request location
    @POST(Constants.URL_SYNC_POST_TOOLHAWK_TRANSFER)
    public void postSyncToolhawkTransfer(@Body List<TransferToolhawk> transferToolhawkList,
                                    Callback<List<SyncPostEquipment>> syncGetResponseDTOCallback);


}

