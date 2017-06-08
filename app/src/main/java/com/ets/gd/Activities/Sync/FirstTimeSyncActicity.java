package com.ets.gd.Activities.Sync;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.Login.LoginActivity;
import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Constants.Constants;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.RequestDTOs.LoginDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncGetDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.LoginResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncGetResponseDTO;
import com.ets.gd.NetworkLayer.Service.GSDServiceFactory;
import com.ets.gd.NetworkLayer.Service.MyCallBack;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

public class FirstTimeSyncActicity extends AppCompatActivity implements MyCallBack {


    Button btnSync;
    SharedPreferencesManager sharedPreferencesManager;
    String customerID;
    ImageView ivBack, ivTick;
    TextView tbTitleTop, tbTitleBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_sync_acticity);
        initViews();
        initObj();
        initListeners();

    }

    private void callSyncGetService() {
        btnSync.setEnabled(false);
        CommonActions.showProgressDialog(FirstTimeSyncActicity.this);
        DataManager.getInstance().deleteRealm();
        //GSDServiceFactory.getService(getApplicationContext()).getSyncData(new SyncGetDTO(Constants.RESPONSE_SYNC_GET, sharedPreferencesManager.getString(SharedPreferencesManager.MY_DEVICE_ID), customerID), this);
        GSDServiceFactory.getService(getApplicationContext()).getSyncData(new SyncGetDTO(Constants.RESPONSE_SYNC_GET, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)), this);
    }

    private void initViews() {
        btnSync = (Button) findViewById(R.id.btnSync);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivTick.setVisibility(View.GONE);
        tbTitleTop.setText("ETS");
        tbTitleBottom.setText("Sync");
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(FirstTimeSyncActicity.this);
        customerID = getIntent().getStringExtra("customerCode");

    }

    private void initListeners() {
        btnSync.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnSync: {
                    callSyncGetService();
                    break;
                }

                case R.id.ivBack: {
                    finish();
                    break;
                }
            }
        }

    };

    @Override
    public void onSuccess(ResponseDTO responseDTO) {

        switch (responseDTO.getCallBackId()) {

            case Constants.RESPONSE_SYNC_GET:

                try {
                    CommonActions.DismissesDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SyncGetResponseDTO syncGetResponseDTO = (SyncGetResponseDTO) responseDTO;
                if (null != responseDTO) {
                    if (null != syncGetResponseDTO && null!=syncGetResponseDTO.getLstCustomerData()  && 0 != syncGetResponseDTO.getLstCustomerData().size()) {


                        DataManager.getInstance().saveSyncGetResponse(syncGetResponseDTO);
                        sharedPreferencesManager.setString(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_CODE, getIntent().getStringExtra("customerCode"));
                       // sharedPreferencesManager.setInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID, syncGetResponseDTO.getCustomerId());
                        startActivity(new Intent(FirstTimeSyncActicity.this, LoginActivity.class));
                        Toast.makeText(getApplicationContext(), "Sync Complete!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        new AlertDialog.Builder(FirstTimeSyncActicity.this)
                                .setTitle("Syncing")
                                .setMessage(""+syncGetResponseDTO.getErrorMsg())
                                .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                    btnSync.setEnabled(true);
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onFailure(ResponseDTO errorDTO) {
        btnSync.setEnabled(true);
        CommonActions.DismissesDialog();
        if (404 == errorDTO.getCode())
            Toast.makeText(getApplicationContext(), R.string.error_404_msg, Toast.LENGTH_LONG).show();
        else if (1 == errorDTO.getCode())
            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
        else if (400 == errorDTO.getCode()) {
            new AlertDialog.Builder(FirstTimeSyncActicity.this)
                    .setTitle(R.string.txt_login)
                    .setMessage(R.string.msg_login_failed)
                    .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else if (500 == errorDTO.getCode())
            Toast.makeText(getApplicationContext(), R.string.error_404_msg, Toast.LENGTH_LONG).show();

        else
            Toast.makeText(getApplicationContext(), R.string.error_con_timeout, Toast.LENGTH_LONG).show();
    }
}
