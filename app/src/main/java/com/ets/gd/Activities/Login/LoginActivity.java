package com.ets.gd.Activities.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Activities.Sync.DeviceRegistrationActivity;
import com.ets.gd.Constants.Constants;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FirebugDashboardFragment;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.LoginDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.LoginResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.MobileUser;
import com.ets.gd.NetworkLayer.ResponseDTOs.RegisteredDevice;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.Service.GSDServiceFactory;
import com.ets.gd.NetworkLayer.Service.MyCallBack;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.RealmList;


public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private CommonActions ca;
    ToggleButton tbSync;
    SharedPreferencesManager sharedPreferencesManager;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;
    RealmList<MobileUser> lstMusers = new RealmList<MobileUser>();
    RealmList<RegisteredDevice> lstDevices;

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
        etUsername.requestFocus();

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            etPassword.setBackgroundColor(Color.parseColor("#ffffff"));
            etUsername.setBackgroundColor(Color.parseColor("#ffffff"));
        }

//        etUsername.setText("eric55");
//        etPassword.setText("1234567");

        //   etUsername.setText("a345");
        //   etPassword.setText("13244567");

    }

    private void initObj() {
        ca = new CommonActions(LoginActivity.this);

        sharedPreferencesManager = new SharedPreferencesManager(LoginActivity.this);
        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.SYNC_STATE)) {
            tbSync.setChecked(true);
        }
        if (null != DataManager.getInstance().getSyncRealmGetResponseDTO()) {
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncRealmGetResponseDTO();
            lstDevices = realmSyncGetResponseDTO.getLstDevices();
            lstMusers.clear();
            for (int i = 0; i < realmSyncGetResponseDTO.getLstCustomerData().size(); i++) {
                lstMusers.addAll(realmSyncGetResponseDTO.getLstCustomerData().get(i).getLstMusers());

            }
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
                    if ("admin".equals(etUsername.getText().toString().trim().toLowerCase())
                            && "admin".equals(etPassword.getText().toString().trim().toLowerCase())) {
                        startActivity(new Intent(LoginActivity.this, DeviceRegistrationActivity.class));
                    } else {
                        if (checkValidation()) {
                            sharedPreferencesManager.setString(SharedPreferencesManager.LOGGED_IN_USER_ID, etUsername.getText().toString().trim());
                            CommonActions.showProgressDialog(LoginActivity.this);
                            if (null != lstMusers) {
                                if (checkMobileUserFromDatabase(etUsername.getText().toString().trim(), etPassword.getText().toString().trim())) {
                                    CommonActions.DismissesDialog();
                                    if (checkDevice()) {
                                        showToast("Login Successful");
                                        startActivity(new Intent(LoginActivity.this, BaseActivity.class));
                                        finish();
                                    } else {
                                        showToast("This Device is not registered!");
                                    }

                                } else {
                                    CommonActions.DismissesDialog();
                                    showToast("Invalid Username/Password");
                                }
                            } else {
                                CommonActions.DismissesDialog();
                                showToast("No mobile user(s) found");
                            }
                        }

                    }
                    break;
                }
            }
        }

    };

    private boolean checkValidation() {

        if ("".equals(etUsername.getText().toString().trim())) {
            showToast("Please enter username");
        } else if ("".equals(etPassword.getText().toString().trim())) {
            showToast("Please enter password");
        } else {
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


    boolean checkMobileUserFromDatabase(String username, String pass) {

        boolean doesUserExist = false;
        String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String encryptedDeviceID = md5(deviceID);
        for (int i = 0; i < lstMusers.size(); i++) {
            if (username.equals(lstMusers.get(i).getUserName().trim()) && pass.equals(lstMusers.get(i).getPassword().trim())) {
                doesUserExist = true;

                break;
            }
        }

        return doesUserExist;
    }


    boolean checkDevice() {
        boolean doesDeviceisRegistered = false;
        String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (null != lstDevices) {
            for (int j = 0; j < lstDevices.size(); j++) {
                if (deviceID.equals(lstDevices.get(j).getDeviceID())) {
                    doesDeviceisRegistered = true;
                    break;
                }
            }
        } else {
            showToast("No Device Registered for Customer " + sharedPreferencesManager.getString(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_CODE));
        }

        return doesDeviceisRegistered;
    }


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    public static final String md5(final String s) {
        //final String MD5 = "54686F6D-6173-4D63-446F-6E616C64";
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
