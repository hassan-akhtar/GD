package com.ets.gd.NetworkLayer.Service;


import com.ets.gd.NetworkLayer.RequestDTOs.LoginDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransferRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncGetDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostUnitInspectionRequestDTO;

public interface GSDService {

     void loginRequest(LoginDTO loginFteDTO, MyCallBack callback);
     void getSyncData(SyncGetDTO syncGetDTO, MyCallBack callback);
     void postSyncEquipment(SyncPostEquipmentRequestDTO syncPostEquipmentRequestDTO, MyCallBack callback);
     void postSyncLocation(SyncPostAddLocationRequestDTO syncPostAddLocationRequestDTO, MyCallBack callback);
     void postMoveTransfer(MoveTransferRequestDTO moveTransferRequestDTO, MyCallBack callback);
     void postInspectEquipment(SyncPostUnitInspectionRequestDTO syncPostUnitInspectionRequestDTO, MyCallBack callback);


}
