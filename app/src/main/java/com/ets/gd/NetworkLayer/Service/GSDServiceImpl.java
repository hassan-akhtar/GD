package com.ets.gd.NetworkLayer.Service;

import android.content.Context;
import android.content.SharedPreferences;

import com.ets.gd.Constants.Constants;
import com.ets.gd.NetworkLayer.RequestDTOs.CheckInForCall;
import com.ets.gd.NetworkLayer.RequestDTOs.CheckOutForCall;
import com.ets.gd.NetworkLayer.RequestDTOs.LoginDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransferRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncCheckInRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncCheckOutRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncGetDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddETSLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentMaintenanceDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostMoveInventoryRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostQuickCountRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostToolhawkEquipment;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostToolhawkMoveDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostUnitInspectionRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncToolhawkTransferDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.LoginResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipmentResponseDTO;
import com.ets.gd.Utils.SharedPreferencesManager;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
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

    @Override
    public void postSyncToolhawkMove(final SyncPostToolhawkMoveDTO syncPostToolhawkMoveDTO, final MyCallBack callback) {
        adapter.postSyncToolhawkMove(syncPostToolhawkMoveDTO.getMoveDTOList(), new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostToolhawkMoveDTO.getCallBackId());
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
    public void postSyncQuickCount(final SyncPostQuickCountRequestDTO syncPostQuickCountRequestDTO, final MyCallBack callback) {
        adapter.postSyncQuickCount(syncPostQuickCountRequestDTO.getLstQuickCount(), new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostQuickCountRequestDTO.getCallBackId());
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
    public void postSyncMaintenace(final SyncPostEquipmentMaintenanceDTO syncPostEquipmentMaintenanceDTO, final MyCallBack callback) {
        adapter.postSyncMaintenace(syncPostEquipmentMaintenanceDTO.getEquipmentMaintenanceList(), new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostEquipmentMaintenanceDTO.getCallBackId());
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
    public void postSyncCheckIn(final SyncCheckInRequestDTO checkIn, final MyCallBack callback) {

        List<CheckInForCall> lstCheckIn =  new ArrayList<CheckInForCall>();
        for (int i=0;i<checkIn.getCheckInList().size();i++) {
            CheckInForCall checkInForCall = new CheckInForCall();
            checkInForCall.setUserID(checkIn.getCheckInList().get(i).getUserID());
            checkInForCall.setJobNumberID(checkIn.getCheckInList().get(i).getJobNumberID());
             List<Integer> EquipmentID =  new ArrayList<Integer>();
            for (int j=0;j<checkIn.getCheckInList().get(i).getEquipmentID().size();j++) {
                EquipmentID.add(checkIn.getCheckInList().get(i).getEquipmentID().get(j).getEquipmentID());
            }
            checkInForCall.setEquipmentIDList(EquipmentID);
            lstCheckIn.add(checkInForCall);
        }


        adapter.postSyncCheckIn(lstCheckIn, new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(checkIn.getCallBackId());
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
    public void postSyncCheckOut(final SyncCheckOutRequestDTO checkOut, final MyCallBack callback) {

        List<CheckOutForCall> lstCheckOut =  new ArrayList<CheckOutForCall>();
        for (int i=0;i<checkOut.getCheckOutList().size();i++) {
            CheckOutForCall checkOutForCall = new CheckOutForCall();
            checkOutForCall.setUserID(checkOut.getCheckOutList().get(i).getUserID());
            checkOutForCall.setCheckOutType(checkOut.getCheckOutList().get(i).getCheckOutType());
            checkOutForCall.setJobNumberID(checkOut.getCheckOutList().get(i).getJobNumberID());
            checkOutForCall.setDueDate(checkOut.getCheckOutList().get(i).getDueDate());
            List<Integer> EquipmentID =  new ArrayList<Integer>();
            for (int j=0;j<checkOut.getCheckOutList().get(i).getEquipmentID().size();j++) {
                EquipmentID.add(checkOut.getCheckOutList().get(i).getEquipmentID().get(j).getEquipmentID());
            }
            checkOutForCall.setEquipmentIDList(EquipmentID);
            lstCheckOut.add(checkOutForCall);
        }

        adapter.postSyncCheckOut(lstCheckOut, new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(checkOut.getCallBackId());
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
    public void postSyncMoveInventory(final SyncPostMoveInventoryRequestDTO syncPostMoveInventoryRequestDTO, final MyCallBack callback) {
        adapter.postSyncMoveInventory(syncPostMoveInventoryRequestDTO.getMoveInventoryList(), new Callback<List<SyncPostEquipment>>() {
            @Override
            public void success(List<SyncPostEquipment> syncPostEquipment, Response response) {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = new SyncPostEquipmentResponseDTO();
                syncPostEquipmentResponseDTO.setSyncPostEquipments(syncPostEquipment);
                syncPostEquipmentResponseDTO.setCallBackId(syncPostMoveInventoryRequestDTO.getCallBackId());
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
