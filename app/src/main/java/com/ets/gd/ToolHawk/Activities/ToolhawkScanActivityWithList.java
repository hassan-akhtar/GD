package com.ets.gd.ToolHawk.Activities;

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

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Adapters.MoveAdapter;
import com.ets.gd.ToolHawk.EquipmentInfo.EquipmentInfoActivity;
import com.ets.gd.ToolHawk.Maintenance.MaintenanceActivity;
import com.ets.gd.ToolHawk.Move.MoveActivity;
import com.ets.gd.ToolHawk.Move.MoveAssetActivity;
import com.ets.gd.ToolHawk.QuickCount.QuickCountActivity;
import com.ets.gd.ToolHawk.Transfer.TransferActivity;

import java.util.ArrayList;
import java.util.List;

public class ToolhawkScanActivityWithList extends AppCompatActivity {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvScanType;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    MoveAdapter mAdapter;
    String taskType, scanType, department;
    ImageView ivBack, ivTick;
    RecyclerView rvList;
    private List<JobNumber> jobNumberList = new ArrayList<JobNumber>();
    private List<ToolhawkEquipment> equipmentList = new ArrayList<ToolhawkEquipment>();
    private List<ETSLocations> etsLocationsList = new ArrayList<ETSLocations>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolhawk_scan_with_list);

        initViews();
        initObj();
        initListeners();
        setupView();
        setupList();

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
        scanType = getIntent().getStringExtra("scanType");
        department = getIntent().getStringExtra("department");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
        tvScanType.setText("Select / Scan " + scanType);
        tvDepartment.setText("" + department);
        tvUnderText.setText("Scan or Enter " + scanType + " ID");
    }

    private void initObj() {
        hideKeyboard();
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);

        rvList.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(ToolhawkScanActivityWithList.this, rvList, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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

    private void setupList() {

        if (scanType.toLowerCase().startsWith("job")) {
            jobNumberList = DataManager.getInstance().getAllJobNumberList();
            mAdapter = new MoveAdapter(ToolhawkScanActivityWithList.this, jobNumberList, "job Number");

        } else if (scanType.toLowerCase().startsWith("loc")) {
            etsLocationsList = DataManager.getInstance().getAllETSLocations();
            mAdapter = new MoveAdapter(etsLocationsList, "loc", ToolhawkScanActivityWithList.this);

        } else if (scanType.toLowerCase().startsWith("asset")) {
            equipmentList = DataManager.getInstance().getAllToolhawkEquipment();
            mAdapter = new MoveAdapter(ToolhawkScanActivityWithList.this, "asset", equipmentList);

        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ToolhawkScanActivityWithList.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {
                    Intent in = new Intent(ToolhawkScanActivityWithList.this, MoveAssetActivity.class);
                    in.putExtra("taskType", taskType);
                    in.putExtra("department", department);
                    in.putExtra("scanType", scanType);
                    in.putExtra("moveCode", "SVHV001");
                    startActivity(in);
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

