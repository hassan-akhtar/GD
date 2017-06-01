package com.ets.gd.ToolHawk.Move;

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

public class MoveAssetActivity extends AppCompatActivity {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvMoveType, tvMoveCode;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    TextView tvCount, tvCountSupportText, tvTaskName;
    RelativeLayout rlForwardArrow, rlMove;
    ImageView ivInfo;
    String taskType, scanType, department, moveCode;
    ImageView ivBack, ivTick;
    boolean isfinalMove = false;
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_asset);

        initViews();
        initObj();
        initListeners();
        setupView();
    }

    private void initViews() {

        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtns);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvMoveType = (TextView) findViewById(R.id.tvReturningUser);
        tvMoveCode = (TextView) findViewById(R.id.tvMoveCode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        rvList = (RecyclerView) findViewById(R.id.rvList);

        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCountSupportText = (TextView) findViewById(R.id.tvCountSupportText);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        rlForwardArrow = (RelativeLayout) findViewById(R.id.rlForwardArrow);
        rlMove = (RelativeLayout) findViewById(R.id.rlMove);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        scanType = getIntent().getStringExtra("scanType");
        department = getIntent().getStringExtra("department");
        moveCode = getIntent().getStringExtra("moveCode");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
        tvDepartment.setText("" + department);
        tvUnderText.setText("Scan or Enter " + scanType + " ID");
        tvMoveType.setText("Move to " + scanType);
        tvMoveCode.setText("" + moveCode);


        tvCount.setText("1");
        tvCountSupportText.setText("Asset Selected To " + tbTitleBottom.getText().toString());
        tvTaskName.setText(" " + tbTitleBottom.getText().toString().toUpperCase());

    }

    private void initObj() {
        hideKeyboard();
    }


    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);
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
                    if (!isfinalMove) {
                        rvList.setVisibility(View.GONE);
                        rlMove.setVisibility(View.VISIBLE);
                        isfinalMove =true;
                    } else {
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

