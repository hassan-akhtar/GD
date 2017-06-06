package com.ets.gd.NetworkLayer.Service;

import android.content.Context;
import android.content.SharedPreferences;

import com.ets.gd.Constants.Constants;
import com.ets.gd.NetworkLayer.RequestDTOs.LoginDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransferRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncGetDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddETSLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostToolhawkEquipment;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostUnitInspectionRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncToolhawkTransferDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.ToolhawkTransferDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.LoginResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipmentResponseDTO;
import com.ets.gd.Utils.SharedPreferencesManager;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class GSDServiceImpl implements GSDService {


    private GSDRESTService adapter;
    SharedPreferences sharedpreferences;
    SharedPreferencesManager sharedPreferencesManager;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "My_Pref";

    GSDServiceImpl(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedPreferencesManager = new SharedPreferencesManager(context);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(Constants.READ_TIMEOUT_SECONDS,
                TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(Constants.CONNECTION_TIMEOUT_SECONDS,
                TimeUnit.SECONDS);
        final RestAdapter retroAdapter = new RestAdapter.Builder()
                .setEndpoint(sharedPreferencesManager.getString(SharedPreferencesManager.BASE_URL))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient)).build();
        adapter = retroAdapter.create(GSDRESTService.class);
    }


    @Override
    public void loginRequest(final LoginDTO loginDTO, final MyCallBack callback) {
        adapter.loginRequest(loginDTO.getUsername(), loginDTO.getPassword(), loginDTO.getGrantType(), new Callback<LoginResponseDTO>() {
            @Override
            public void success(LoginResponseDTO loginResponseDTO, Response response) {
                loginResponseDTO.setCallBackId(loginDTO.getCallBackId());
                editor = sharedpreferences.edit();
                editor.putString("AuthToken", loginResponseDTO.getAccess_token());
                editor.commit();
                callback.onSuccess(loginResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });

    }

    @Override
    public void getSyncData(final SyncGetDTO syncGetDTO, final MyCallBack callback) {
        adapter.getSyncData(syncGetDTO.getDeviceId(), new Callback<SyncGetResponseDTO>() {
            @Override
            public void success(SyncGetResponseDTO syncGetResponseDTO, Response response) {
                syncGetResponseDTO.setCallBackId(syncGetDTO.getCallBackId());
                callback.onSuccess(syncGetResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });
    }

    @Override
    public void postSyncEquipment(final SyncPostEquipmentRequestDTO syncPostEquipmentRequestDTO, final MyCallBack callback) {
        adapter.postSyncEquipment(syncPostEquipmentRequestDTO, new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostEquipmentRequestDTO.getCallBackId());
                callback.onSuccess(syncPostEquipmentResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });
    }

    @Override
    public void postSyncLocation(final SyncPostAddLocationRequestDTO syncPostAddLocationRequestDTO, final MyCallBack callback) {
        adapter.postSyncLocation(syncPostAddLocationRequestDTO, new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostAddLocationRequestDTO.getCallBackId());
                callback.onSuccess(syncPostEquipmentResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });
    }

    @Override
    public void postMoveTransfer(final MoveTransferRequestDTO moveTransferRequestDTO, final MyCallBack callback) {
        adapter.postMoveTransfer(moveTransferRequestDTO, new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(moveTransferRequestDTO.getCallBackId());
                callback.onSuccess(syncPostEquipmentResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });
    }

    @Override
    public void postInspectEquipment(final SyncPostUnitInspectionRequestDTO syncPostUnitInspectionRequestDTO, final MyCallBack callback) {
        adapter.postInspectEquipment(syncPostUnitInspectionRequestDTO, new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostUnitInspectionRequestDTO.getCallBackId());
                callback.onSuccess(syncPostEquipmentResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });
    }

    @Override
    public void postSyncToolhawkEquipment(final SyncPostToolhawkEquipment syncPostToolhawkEquipment, final MyCallBack callback) {
        adapter.postSyncToolhawkEquipment(syncPostToolhawkEquipment, new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostToolhawkEquipment.getCallBackId());
                callback.onSuccess(syncPostEquipmentResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });
    }

    @Override
    public void postSyncETSLocation(final SyncPostAddETSLocationRequestDTO syncPostAddETSLocationRequestDTO, final MyCallBack callback) {
        adapter.postSyncETSLocation(syncPostAddETSLocationRequestDTO.getLstAddLocation(), new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostAddETSLocationRequestDTO.getCallBackId());
                callback.onSuccess(syncPostEquipmentResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });
    }

    @Override
    public void postSyncToolhawkTransfer(final SyncToolhawkTransferDTO syncToolhawkTransferDTO, final MyCallBack callback) {
        adapter.postSyncToolhawkTransfer(syncToolhawkTransferDTO.getTransferToolhawkList(), new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncToolhawkTransferDTO.getCallBackId());
                callback.onSuccess(syncPostEquipmentResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null && error.getResponse() != null && error.getResponse().getStatus() != 0) {
                    callback.onFailure(new ResponseDTO(error.getMessage(), error.getResponse().getStatus()));
                } else {
                    callback.onFailure(new ResponseDTO(error.getMessage(), 1));
                }
            }
        });
    }


}
