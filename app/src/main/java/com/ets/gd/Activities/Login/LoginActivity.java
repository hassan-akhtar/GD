package com.ets.gd.Activities.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Constants.Constants;
import com.ets.gd.NetworkLayer.RequestDTOs.LoginDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.LoginResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.Service.GSDServiceFactory;
import com.ets.gd.NetworkLayer.Service.MyCallBack;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;


public class LoginActivity extends AppCompatActivity implements MyCallBack {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private CommonActions ca;
    ToggleButton tbSync;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initObj();
        initListeners();
    }


    private void initViews() {
        etUsername = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tbSync = (ToggleButton) findViewById(R.id.tbSync);
        etUsername.setText("eric55");
        etPassword.setText("1234567");
    }

    private void initObj() {
        ca = new CommonActions(LoginActivity.this);
        sharedPreferencesManager = new SharedPreferencesManager(LoginActivity.this);
        if(sharedPreferencesManager.getBoolean(SharedPreferencesManager.SYNC_STATE)){
            tbSync.setChecked(true);
        }
    }

    private void initListeners() {
        btnLogin.setOnClickListener(mGlobal_OnClickListener);
    }


    private final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnLogin: {
                    saveSyncState();
                    if (checkValidation()) {
                        loginCall();
                    }
                    break;
                }
            }
        }

    };

    private boolean checkValidation() {

        if("".equals(etUsername.getText().toString().trim())){
            showToast("Please enter username");
        }else if("".equals(etPassword.getText().toString().trim())){
            showToast("Please enter password");
        }else {
            return true;
        }

        return false;
    }

    void saveSyncState() {

        if (tbSync.isChecked())
            sharedPreferencesManager.setBoolean(SharedPreferencesManager.SYNC_STATE, true);
        else
            sharedPreferencesManager.setBoolean(SharedPreferencesManager.SYNC_STATE, false);

    }

    void loginCall() {
        CommonActions.showProgressDialog(LoginActivity.this);
        GSDServiceFactory.getService(getApplicationContext()).loginRequest(new LoginDTO(Constants.RESPONSE_LOGIN, etUsername.getText().toString().toString(), etPassword.getText().toString().trim(), "password"), this);
    }

    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onSuccess(ResponseDTO responseDTO) {

        switch (responseDTO.getCallBackId()) {

            case Constants.RESPONSE_LOGIN:
                LoginResponseDTO loginResponseDTO = (LoginResponseDTO) responseDTO;
                if (responseDTO != null) {
                    if (null != loginResponseDTO.getAccess_token()) {
                        CommonActions.DismissesDialog();
                        startActivity(new Intent(LoginActivity.this, BaseActivity.class));
                        finish();
                    } else {
                        CommonActions.DismissesDialog();
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle(R.string.txt_login)
                                .setMessage(R.string.msg_login_failed)
                                .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onFailure(ResponseDTO errorDTO) {
        CommonActions.DismissesDialog();
        if (404 == errorDTO.getCode())
            Toast.makeText(getApplicationContext(), R.string.error_404_msg, Toast.LENGTH_LONG).show();
        else if (1 == errorDTO.getCode())
            Toast.makeText(getApplicationContext(), R.string.error_poor_con, Toast.LENGTH_LONG).show();
        else if(400 == errorDTO.getCode())
        {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(R.string.txt_login)
                    .setMessage(R.string.msg_login_failed)
                    .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
       else  if (500 == errorDTO.getCode())
            Toast.makeText(getApplicationContext(), R.string.error_404_msg, Toast.LENGTH_LONG).show();

        else
            Toast.makeText(getApplicationContext(), R.string.error_con_timeout, Toast.LENGTH_LONG).show();
    }
}
