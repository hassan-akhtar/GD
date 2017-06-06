package com.ets.gd.NetworkLayer.Service;


import com.ets.gd.NetworkLayer.RequestDTOs.LoginDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransferRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncGetDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddETSLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostToolhawkEquipment;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostUnitInspectionRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncToolhawkTransferDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.TransferToolhawk;

import java.util.List;

public interface GSDService {

    void loginRequest(LoginDTO loginFteDTO, MyCallBack callback);

    void getSyncData(SyncGetDTO syncGetDTO, MyCallBack callback);

    void postSyncEquipment(SyncPostEquipmentRequestDTO syncPostEquipmentRequestDTO, MyCallBack callback);

    void postSyncLocation(SyncPostAddLocationRequestDTO syncPostAddLocationRequestDTO, MyCallBack callback);

    void postMoveTransfer(MoveTransferRequestDTO moveTransferRequestDTO, MyCallBack callback);

    void postInspectEquipment(SyncPostUnitInspectionRequestDTO syncPostUnitInspectionRequestDTO, MyCallBack callback);

    void postSyncToolhawkEquipment(SyncPostToolhawkEquipment syncPostToolhawkEquipment, MyCallBack callback);

    void postSyncETSLocation(SyncPostAddETSLocationRequestDTO syncPostAddETSLocationRequestDTO, MyCallBack callback);

    void postSyncToolhawkTransfer(SyncToolhawkTransferDTO syncToolhawkTransferDTO, MyCallBack callback);
}
