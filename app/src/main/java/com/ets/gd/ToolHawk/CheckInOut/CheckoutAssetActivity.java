package com.ets.gd.ToolHawk.CheckInOut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;

public class CheckoutAssetActivity extends AppCompatActivity {

    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvReturningUser, tvJobNumberCode;
    TextView tvCount, tvCountSupportText, tvTaskName;
    TextView tvAssetTextandCount, tvAssets, tvUser;
    RelativeLayout rlForwardArrow, rlCheckInOut, rlListArea;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    LinearLayout llJobNumber, llUser;
    RecyclerView rvList;
    String taskType, department, returningUser, JobNumber;
    ImageView ivBack, ivTick;
    boolean isfinalCheckout = false, isUser= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_asset);


        initViews();
        initObj();
        initListeners();
        setupView();

    }

    private void initViews() {

        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtnsQuickCount);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvReturningUser = (TextView) findViewById(R.id.tvReturningUserName);
        tvJobNumberCode = (TextView) findViewById(R.id.tvJobNumberCode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        llJobNumber = (LinearLayout) findViewById(R.id.llJobNumber);
        llUser = (LinearLayout) findViewById(R.id.llUser);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        rlListArea = (RelativeLayout) findViewById(R.id.rlScanArea);

        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCountSupportText = (TextView) findViewById(R.id.tvCountSupportText);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        rlForwardArrow = (RelativeLayout) findViewById(R.id.rlForwardArrow);

        rlCheckInOut = (RelativeLayout) findViewById(R.id.rlCheckInOut);
        tvAssetTextandCount = (TextView) findViewById(R.id.tvAssetTextandCount);
        tvAssets = (TextView) findViewById(R.id.tvAssets);
        tvUser = (TextView) findViewById(R.id.tvUser);


        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        department = getIntent().getStringExtra("department");
        returningUser = getIntent().getStringExtra("returningUser");
        JobNumber = getIntent().getStringExtra("JobNumber");
        isUser = getIntent().getBooleanExtra("isUser",false);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
        tvDepartment.setText("" + department);
        tvUnderText.setText("Scan or Enter asset ID");
        tvReturningUser.setText("" + returningUser);
        tvJobNumberCode.setText("" + JobNumber);


        tvCount.setText("2");
        if(tbTitleBottom.getText().toString().toLowerCase().startsWith("check out")){
            tvAssetTextandCount.setText("Checking out 1 Asset");
        }else{
            tvAssetTextandCount.setText("Checking In 1 Asset");
        }

        tvCountSupportText.setText("Asset Selected To "+tbTitleBottom.getText().toString());
        tvTaskName.setText(" "+tbTitleBottom.getText().toString().toUpperCase());



        if (null == JobNumber) {
            llJobNumber.setVisibility(View.GONE);
        } else {
            llJobNumber.setVisibility(View.VISIBLE);
        }


        if (!isUser) {
            llUser.setVisibility(View.GONE);
        }
    }

    private void initObj() {
        hideKeyboard();
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);

    }

    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        llbtns.setVisibility(View.GONE);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {

                    break;
                }

                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.rlForwardArrow: {
                    if (!isfinalCheckout) {
                        rlCheckInOut.setVisibility(View.VISIBLE);
                        rlListArea.setVisibility(View.GONE);
                        tvCount.setText("");
                        tvCountSupportText.setText("Are you sure you want to "+ tbTitleBottom.getText().toString() +" 1 Asset");
                        tvTaskName.setText(""+tbTitleBottom.getText().toString().toUpperCase());
                        isfinalCheckout = true;
                    }else{
                        showToast("yoo");
                    }
                    break;
                }
            }
        }

    };


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        return false;
    }
}


