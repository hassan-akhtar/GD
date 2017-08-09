package com.ets.gd.ToolHawk.Transfer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.RequestDTOs.ToolhawkTransferDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.Department;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransferActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {


    TextView tbTitleTop, tbTitleBottom, tvDepartment, tvEquip, tvTransfer, tvCancel;
    String departmentName, eqName, taskName;
    ImageView ivBack, ivTick;
    Spinner spDep, spLoc;
    List<Department> depList = new ArrayList<Department>();
    List<ETSLocations> locList = new ArrayList<ETSLocations>();
    ToolhawkTransferDTO toolhawkEquipment;
    ToolhawkEquipment theq;
    Department dep;
    int posDepartment, posLoc;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        initViews();
        initObj();
        initListeners();
        setupView();
    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvTransfer = (TextView) findViewById(R.id.tvTransfer);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvEquip = (TextView) findViewById(R.id.tvEquip);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        spDep = (Spinner) findViewById(R.id.spDep);
        spLoc = (Spinner) findViewById(R.id.spLoc);
        taskName = getIntent().getStringExtra("taskName");
        eqName = getIntent().getStringExtra("eqName");

        theq = DataManager.getInstance().getToolhawkEquipment(eqName);
        tvEquip.setText("" + eqName);
        if(null!=theq){
            dep = DataManager.getInstance().getDepartmentCodeByEquipmentCode(eqName);
            departmentName = dep.getCode();
            tvDepartment.setText("" + departmentName);
        }

        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskName);

    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(TransferActivity.this);
        depList = DataManager.getInstance().getAllDepartments();


        if (null != depList) {
            int sizeDepartment = depList.size() + 1;
            String[] departments = new String[sizeDepartment];
            departments[0] = "Please select a department";
            for (int i = 0; i < depList.size(); i++) {
                departments[i + 1] = depList.get(i).getCode();
            }

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            ArrayAdapter<String> dataAdapterDeviceType = new ArrayAdapter<String>(TransferActivity.this, android.R.layout.simple_spinner_item, departments);
            dataAdapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDep.setAdapter(dataAdapterDeviceType);

        }

            String[] locations = new String[1];
            locations[0] = "Please select a location";
            ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(TransferActivity.this, android.R.layout.simple_spinner_item, locations);
            dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spLoc.setAdapter(dataAdapterVendor);


    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvTransfer.setOnClickListener(mGlobal_OnClickListener);
        tvCancel.setOnClickListener(mGlobal_OnClickListener);
        spDep.setOnItemSelectedListener(this);
        spLoc.setOnItemSelectedListener(this);
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
    }


    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.tvTransfer: {

                    toolhawkEquipment = new ToolhawkTransferDTO(theq.getCode(),DataManager.getInstance().getDepartmentByCode(spDep.getItemAtPosition(posDepartment).toString()).getID(),
                            DataManager.getInstance().getETSLocationsByCode(spLoc.getItemAtPosition(posLoc).toString()).getID());
                    DataManager.getInstance().saveResultTransferToolhawk(toolhawkEquipment);
                    showToast("Transfer Complete!");
                    sendMessage("finish");
                    finish();
                    break;
                }

                case R.id.tvCancel: {
                    finish();
                    break;
                }

            }
        }

    };


    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("move-complete");
        intent.putExtra("message", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spDep: {
                posDepartment = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    //tvLableDeviceType.setVisibility(View.GONE);
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (null!=DataManager.getInstance().getDepartmentByCode(strSelectedState)) {
                        locList = DataManager.getInstance().getAllDepETSLocations(DataManager.getInstance().getDepartmentByCode(strSelectedState).getID());
                    }
                    if (null != locList) {
                        int sizeLocations = locList.size() + 1;
                        String[] locations = new String[sizeLocations];
                        locations[0] = "Please select a location";
                        for (int i = 0; i < locList.size(); i++) {
                            locations[i + 1] = locList.get(i).getCode();
                        }

                        ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(TransferActivity.this, android.R.layout.simple_spinner_item, locations);
                        dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spLoc.setAdapter(dataAdapterVendor);
                    }
                }

            }
            break;


            case R.id.spLoc: {
                posLoc = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    //   tvLableVendor.setVisibility(View.GONE);
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //  tvLableVendor.setVisibility(View.VISIBLE);
                }

            }
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
