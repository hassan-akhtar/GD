package com.ets.gd.ToolHawk.EquipmentInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.ResponseDTOs.Department;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer;
import com.ets.gd.NetworkLayer.ResponseDTOs.Model;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class EquipmentInfoActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {


    TextView tbTitleTop, tbTitleBottom, tvEquipmentCode, tvUnitCost;
    String taskType, barcodeID;
    ImageView ivBack, ivTick;
    ToolhawkEquipment toolhawkEquipment;
    Spinner spDepartment, spLocation, spManufacturer, spModel;
    List<Department> depList = new ArrayList<Department>();
    List<ETSLocations> locList = new ArrayList<ETSLocations>();
    List<Manufacturer> manuList = new ArrayList<Manufacturer>();
    int posDepartment, posManufacturer, posModel, posLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_info);
        initViews();
        initObj();
        initListeners();
        setupView();
        hideKeyboard();
    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvEquipmentCode = (TextView) findViewById(R.id.tvEquipmentCode);
        tvUnitCost = (TextView) findViewById(R.id.tvUnitCost);
        spDepartment = (Spinner) findViewById(R.id.spDepartment);
        spLocation = (Spinner) findViewById(R.id.spLocation);
        spManufacturer = (Spinner) findViewById(R.id.spManufacturer);
        spModel = (Spinner) findViewById(R.id.spModel);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        taskType = getIntent().getStringExtra("taskType");
        barcodeID = getIntent().getStringExtra("barcodeID");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("Equipment Info");


    }

    private void initObj() {
        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(barcodeID);
        depList = DataManager.getInstance().getAllDepartments();
        locList = DataManager.getInstance().getAllETSLocations();

        if (null != DataManager.getInstance().getSyncGetResponse()) {
            manuList = DataManager.getInstance().getSyncGetResponse().getLstManufacturers();
        }

        if (null != depList) {
            int sizeDepartment = depList.size() + 1;
            String[] departments = new String[sizeDepartment];
            departments[0] = "Please select a department";
            for (int i = 0; i < depList.size(); i++) {
                departments[i + 1] = depList.get(i).getCode();
            }

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            ArrayAdapter<String> dataAdapterDeviceType = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, departments);
            dataAdapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDepartment.setAdapter(dataAdapterDeviceType);

            if (taskType.startsWith("vie")) {
                for (int i = 0; i < depList.size() + 1; i++) {
                    if (null != toolhawkEquipment.getDepartment()) {
                        if (toolhawkEquipment.getDepartment().getCode().toLowerCase().equals(spDepartment.getItemAtPosition(i).toString().toLowerCase())) {
                            spDepartment.setSelection(i);
                            posDepartment = i;
                            break;
                        }
                    }
                }
            }

        }


        if (null != manuList) {
            int sizeManufacturers = manuList.size() + 1;
            String[] manufacturers = new String[sizeManufacturers];
            manufacturers[0] = "Please select a manufacturer";
            for (int i = 0; i < manuList.size(); i++) {
                manufacturers[i + 1] = manuList.get(i).getCode();
            }
            ArrayAdapter<String> dataAdapterManufacturer = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, manufacturers);
            dataAdapterManufacturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spManufacturer.setAdapter(dataAdapterManufacturer);

            if (taskType.startsWith("vie")) {
                for (int i = 0; i < manuList.size() + 1; i++) {
                    if (null != toolhawkEquipment.getManufacturer()) {
                        if (toolhawkEquipment.getManufacturer().getCode().toLowerCase().equals(spManufacturer.getItemAtPosition(i).toString().toLowerCase())) {
                            spManufacturer.setSelection(i);
                            posManufacturer = i;
                            break;
                        }
                    }
                }
            }
        }


        if (null != locList) {
            int sizeLocations = locList.size() + 1;
            String[] locations = new String[sizeLocations];
            locations[0] = "Please select a location";
            for (int i = 0; i < locList.size(); i++) {
                locations[i + 1] = locList.get(i).getCode();
            }

            ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, locations);
            dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spLocation.setAdapter(dataAdapterVendor);

            if (taskType.startsWith("vie")) {
                for (int i = 0; i < locList.size() + 1; i++) {
                    if (null != toolhawkEquipment.getETSLocation()) {
                        if (toolhawkEquipment.getETSLocation().getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                            spLocation.setSelection(i);
                            posLoc = i;
                            break;
                        }
                    }
                }
            }
        }


        String[] models = new String[1];
        models[0] = "Please select a model";
        ArrayAdapter<String> dataAdapterModel = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, models);
        dataAdapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModel.setAdapter(dataAdapterModel);


    }


    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
        spDepartment.setOnItemSelectedListener(this);
        spLocation.setOnItemSelectedListener(this);
        spManufacturer.setOnItemSelectedListener(this);
        spModel.setOnItemSelectedListener(this);

        spDepartment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });



        spLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });




        spManufacturer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });


        spModel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });


    }

    private void setupView() {
        if (taskType.startsWith("vie")) {
            tvEquipmentCode.setEnabled(false);
            tvEquipmentCode.setText("" + toolhawkEquipment.getCode());
            tvUnitCost.setText("" + toolhawkEquipment.getUnitCost());

        } else {
            tvEquipmentCode.setEnabled(true);
        }

    }


    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                tvEquipmentCode.getWindowToken(), 0);
        InputMethodManager imm2 = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.hideSoftInputFromWindow(
                tvUnitCost.getWindowToken(), 0);

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivTick: {

                    ToolhawkEquipment equipment = null;
                    if (checkValidation()) {

                        if (taskType.startsWith("vie")) {
                            equipment = new ToolhawkEquipment(
                                    toolhawkEquipment.getID(),
                                    tvEquipmentCode.getText().toString(),
                                    tvUnitCost.getText().toString(),
                                    DataManager.getInstance().getDepartmentByCode(spDepartment.getItemAtPosition(posDepartment).toString()),
                                    DataManager.getInstance().getETSLocationByCode(spLocation.getItemAtPosition(posLoc).toString()),
                                    DataManager.getInstance().getAssetManufacturer(spManufacturer.getItemAtPosition(posManufacturer).toString()),
                                    DataManager.getInstance().getAssetModel(spModel.getItemAtPosition(posModel).toString()),
                                    toolhawkEquipment.isAdded(),
                                    true
                            );
                            DataManager.getInstance().addToolHawkEquipment(equipment);
                            showToast("Asset Updated!");
                            sendMessage("finish");
                            finish();
                        } else {
                            ToolhawkEquipment eq = DataManager.getInstance().getToolhawkEquipment(tvEquipmentCode.getText().toString());
                            if (null==eq) {
                                equipment = new ToolhawkEquipment(
                                        0,
                                        tvEquipmentCode.getText().toString(),
                                        tvUnitCost.getText().toString(),
                                        DataManager.getInstance().getDepartmentByCode(spDepartment.getItemAtPosition(posDepartment).toString()),
                                        DataManager.getInstance().getETSLocationByCode(spLocation.getItemAtPosition(posLoc).toString()),
                                        DataManager.getInstance().getAssetManufacturer(spManufacturer.getItemAtPosition(posManufacturer).toString()),
                                        DataManager.getInstance().getAssetModel(spModel.getItemAtPosition(posModel).toString()),
                                        true,
                                        false
                                );
                                DataManager.getInstance().addToolHawkEquipment(equipment);
                                showToast("Asset Added!");
                                finish();
                            }else{
                                showToast("Asset with Equipment ID "+ tvEquipmentCode.getText().toString()+" Already Exist!");
                            }
                        }


                    }
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
    private boolean checkValidation() {
        if ("".equals(tvEquipmentCode.getText().toString().trim())) {
            showToast("Please enter Equipment ID");
        } else if (0 == posDepartment) {
            showToast("Please select a Department");
        } else if (0 == posLoc) {
            showToast("Please select a Location");
        } else if (0 == posManufacturer) {
            showToast("Please select a Manufacturer");
        } else if (0 == posModel) {
            showToast("Please select a Model");
        } else {
            return true;
        }

        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spDepartment: {
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
                    //  tvLableDeviceType.setVisibility(View.VISIBLE);
                }

            }
            break;

            case R.id.spManufacturer: {
                posManufacturer = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    //   tvLableManufacturer.setVisibility(View.GONE);
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //   tvLableManufacturer.setVisibility(View.VISIBLE);
                }

                if (0 != posManufacturer) {
                    int macufacturerID = DataManager.getInstance().getAssetManufacturer(strSelectedState).getID();
                    List<Model> modelsList = new ArrayList<Model>();
                    if (0 != macufacturerID) {
                        modelsList = DataManager.getInstance().getModelFromManufacturerID(macufacturerID);
                        if (0 != modelsList.size()) {
                            int sizeModels = modelsList.size() + 1;
                            String[] models = new String[sizeModels];
                            for (int i = 0; i < modelsList.size(); i++) {
                                models[i + 1] = modelsList.get(i).getCode();
                            }
                            models[0] = "Please select a model";
                            ArrayAdapter<String> dataAdapterModel = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, models);
                            dataAdapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spModel.setAdapter(dataAdapterModel);
                        } else {

                            String[] models = new String[1];
                            models[0] = "Please select a model";
                            ArrayAdapter<String> dataAdapterModel = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, models);
                            dataAdapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spModel.setAdapter(dataAdapterModel);
                        }
                    } else {
                        showToast("Manufacturer Id not found");
                    }

                    if (!"addAsset".equals(getIntent().getStringExtra("taskType"))) {
                        for (int i = 0; i < modelsList.size() + 1; i++) {
                            if (null!=toolhawkEquipment && null!=toolhawkEquipment.getModel()) {
                                if (toolhawkEquipment.getModel().getCode().toLowerCase().equals(spModel.getItemAtPosition(i).toString().toLowerCase())) {
                                    spModel.setSelection(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            break;

            case R.id.spModel: {
                posModel = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    //  tvLableModel.setVisibility(View.GONE);
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // tvLableModel.setVisibility(View.VISIBLE);
                }

            }
            break;


            case R.id.spLocation: {
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


    void showToast(String msg) {
        Toast.makeText(EquipmentInfoActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
