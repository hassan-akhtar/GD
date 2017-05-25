package com.ets.gd.ToolHawk.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;
import com.ets.gd.ToolHawk.EquipmentInfo.EquipmentInfoActivity;
import com.ets.gd.ToolHawk.MaintenanceActivity;

public class CommonToolhawkScanActivity extends AppCompatActivity {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText;
    Button btnCross, btnNewCount, btnExistingCount, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    String taskType;
    ImageView ivBack, ivTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_toolhawk_scan);

        initViews();
        initObj();
        initListeners();
        setupView();

    }


    private void initViews() {

        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnNewCount = (Button) findViewById(R.id.btnNewCount);
        btnExistingCount = (Button) findViewById(R.id.btnExistingCount);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtns);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
    }

    private void initObj() {
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnNewCount.setOnClickListener(mGlobal_OnClickListener);
        btnExistingCount.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnCross: {
                    tvBarcodeTitle.setVisibility(View.GONE);
                    tvBarcodeValue.setVisibility(View.GONE);
                    ivInfo.setVisibility(View.VISIBLE);
                    tvUnderText.setVisibility(View.VISIBLE);
                    llbtns.setVisibility(View.GONE);
                    tvBarcodeValue.setText("");
                    etBarcode.setVisibility(View.VISIBLE);
                    etBarcode.setText("");
                    btnCross.setVisibility(View.GONE);
                    break;
                }

                case R.id.btnNewCount: {
                    showToast("New");
                    break;
                }
                case R.id.btnExistingCount: {
                    showToast("Existing");
                    break;
                }
                case R.id.btnScan: {
                    if (tbTitleBottom.getText().toString().toLowerCase().startsWith("eq")) {
                        Intent in = new Intent(CommonToolhawkScanActivity.this, EquipmentInfoActivity.class);
                        in.putExtra("taskType", "view");
                        startActivity(in);
                    } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("qu")) {
                        showViewForQuickCount();

                    } else {
                        Intent in = new Intent(CommonToolhawkScanActivity.this, MaintenanceActivity.class);
                        in.putExtra("assetID", "200020");
                        startActivity(in);
                    }
                    break;
                }

                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivTick: {

                    break;
                }
            }
        }

    };

    private void showViewForQuickCount() {
        llbtns.setVisibility(View.VISIBLE);
        tvBarcodeTitle.setVisibility(View.VISIBLE);
        tvBarcodeValue.setVisibility(View.VISIBLE);
        tvBarcodeValue.setText("Asset1");
        etBarcode.setVisibility(View.GONE);
        ivInfo.setVisibility(View.GONE);
        tvUnderText.setVisibility(View.GONE);
        etBarcode.setText("");
        btnCross.setVisibility(View.VISIBLE);
    }

    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        return false;
    }
}
