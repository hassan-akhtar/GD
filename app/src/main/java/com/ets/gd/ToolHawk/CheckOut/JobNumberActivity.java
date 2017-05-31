package com.ets.gd.ToolHawk.CheckOut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Models.Department;
import com.ets.gd.Models.JobNumber;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.CommonToolhawkDepartmentActivity;
import com.ets.gd.ToolHawk.Activities.ToolhawkScanActivityWithList;
import com.ets.gd.ToolHawk.Adapters.DepartmentAdapter;
import com.ets.gd.ToolHawk.Adapters.JobNumberAdapter;
import com.ets.gd.ToolHawk.Move.MoveAssetActivity;

import java.util.ArrayList;
import java.util.List;

public class JobNumberActivity extends AppCompatActivity {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvScanType;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    String taskType, department;
    ImageView ivBack, ivTick;
    RecyclerView rvList;
    private List<JobNumber> jobNumberList = new ArrayList<JobNumber>();
    JobNumberAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_number);

        initViews();
        initObj();
        initListeners();
        setupView();
        setupDummyJobNumbers();
        hideKeyboard();

    }


    private void initViews() {
        rvList = (RecyclerView) findViewById(R.id.rvList);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtns);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvScanType = (TextView) findViewById(R.id.tvScanType);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        department = getIntent().getStringExtra("department");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
        tvScanType.setText("Select / Scan Job Number");
        tvDepartment.setText("" + department);
        tvUnderText.setText("Scan or Enter Job Number");
    }

    private void initObj() {
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        llbtns.setVisibility(View.GONE);
    }

    private void setupDummyJobNumbers() {

        JobNumber jobNumber = new JobNumber();
        jobNumber.setCode("SVHS001");
        jobNumber.setName("Spring Valley 1");
        jobNumberList.add(jobNumber);
        jobNumber = new JobNumber();
        jobNumber.setCode("SVHS002");
        jobNumber.setName("Spring Valley 2");
        jobNumberList.add(jobNumber);

        mAdapter = new JobNumberAdapter(JobNumberActivity.this,jobNumberList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(JobNumberActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }


    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {
                   showToast("yoo");
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


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        return false;
    }
}


