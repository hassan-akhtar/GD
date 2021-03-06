package com.ets.gd.FireBug.Scan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.R;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class BarcodeScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private String barCode;
    private String taskType;
    public static BarcodeScan barcodeScan;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_barcode_scan);

        taskType = getIntent().getStringExtra("taskType");
        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZBarScannerView(this);
        contentFrame.addView(mScannerView);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(@NonNull Result rawResult) {
            barCode = rawResult.getContents();
            sendMessage(barCode);
            mScannerView.stopCamera();
            finish();


    }


    private void sendMessage(String msg) {
        barcodeScan.BarcodeScanned(new Barcode(msg, taskType));
    }

}