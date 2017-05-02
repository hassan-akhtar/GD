package com.ets.gd.Activities.Sync;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.Login.LoginActivity;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

public class DeviceRegistrationActivity extends AppCompatActivity {

    EditText etCustomerID;
    Button btnNext;
    TextView tvDeviceID;
    ImageView ivBack, ivTick;
    TextView tbTitleTop, tbTitleBottom;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_registration);

        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {
        etCustomerID = (EditText) findViewById(R.id.etCustomerID);
        btnNext = (Button) findViewById(R.id.btnNext);
        tvDeviceID = (TextView) findViewById(R.id.tvDeviceID);
        tbTitleBottom  = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivTick.setVisibility(View.GONE);
        tbTitleTop.setText("ETS");
        tbTitleBottom.setText("Device Registration");
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(DeviceRegistrationActivity.this);
        tvDeviceID.setText("" + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));

    }

    private void initListeners() {
        btnNext.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {

                case R.id.ivBack: {
                    finish();
                    break;
                }


                case R.id.btnNext: {

                    if (!"".equals(etCustomerID.getText().toString().trim())) {
                        //sharedPreferencesManager.setString(SharedPreferencesManager.MY_SYNC_CUSTOMER_ID,etCustomerID.getText().toString().trim());
                        Intent in = new Intent(DeviceRegistrationActivity.this, FirstTimeSyncActicity.class);
                        in.putExtra("customerCode", etCustomerID.getText().toString().trim());
                        startActivity(in);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter your Customer ID", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        }

    };
}
