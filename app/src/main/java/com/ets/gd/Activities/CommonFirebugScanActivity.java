package com.ets.gd.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;

import java.util.Date;

public class CommonFirebugScanActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany;
    EditText etBarcode;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvUnderText, tvBarcodeTitle, tvBarcodeValue;
    Button btnLoc, btnAsset, btnScan, btnCross;
    View vLine;
    String taskType, compName;
    LinearLayout llbtns, llunderText;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_firebug);
        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        vLine = (View) findViewById(R.id.vLine);
        btnLoc = (Button) findViewById(R.id.btnLoc);
        btnAsset = (Button) findViewById(R.id.btnAsset);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtns);
        llunderText = (LinearLayout) findViewById(R.id.llunderText);
        ivChangeCompany.setVisibility(View.GONE);
    }

    private void initObj() {
        mContext = this;
        taskType = getIntent().getStringExtra("taskType");
        compName = getIntent().getStringExtra("compName");
        tbTitleBottom.setText(taskType);
        tvCompanyValue.setText(compName);
        if (taskType.toLowerCase().startsWith("v")) {
            tvUnderText.setText("Enter ID of Asset/Location or tap Scan");
        } else {
            tvUnderText.setText("Enter Asset ID or tap Scan");
        }
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBarcodeBroadcastReceiver,
                new IntentFilter("barcode-scanned"));
    }

    private void initListeners() {
        btnLoc.setOnClickListener(mGlobal_OnClickListener);
        btnAsset.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        ivChangeCompany.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {
                    startActivity(new Intent(CommonFirebugScanActivity.this, BarcodeScanActivity.class));
                    break;
                }

                case R.id.btnAsset: {
                    Intent in = new Intent(CommonFirebugScanActivity.this, ViewAssetInformationActivity.class);
                    startActivity(in);
                    hideScannedData();

                    break;
                }

                case R.id.btnLoc: {
                    Intent in = new Intent(CommonFirebugScanActivity.this, ViewLocationInformationActivity.class);
                    startActivity(in);
                    hideScannedData();
                    break;
                }

                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivChangeCompany: {
                    finish();
                    break;
                }

                case R.id.btnCross: {
                    if (taskType.toLowerCase().startsWith("v")) {
                        hideScannedData();
                    }
                    break;
                }
            }
        }

    };


    private final BroadcastReceiver mBarcodeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            if (taskType.toLowerCase().startsWith("v")) {
                tvBarcodeValue.setText(message);
                etBarcode.setVisibility(View.INVISIBLE);
                tvBarcodeTitle.setVisibility(View.VISIBLE);
                tvBarcodeValue.setVisibility(View.VISIBLE);
                btnCross.setVisibility(View.VISIBLE);
                llunderText.setVisibility(View.GONE);
                llbtns.setVisibility(View.VISIBLE);
            }else{
                showToast(message);
            }

        }
    };

    void hideScannedData() {
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        llunderText.setVisibility(View.VISIBLE);
        etBarcode.setVisibility(View.VISIBLE);
        llbtns.setVisibility(View.GONE);
    }

    void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
