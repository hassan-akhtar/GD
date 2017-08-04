package com.ets.gd.ToolHawk.EquipmentInfo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.ResponseDTOs.Category;
import com.ets.gd.NetworkLayer.ResponseDTOs.Department;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentLocationInfo;
import com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer;
import com.ets.gd.NetworkLayer.ResponseDTOs.Model;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Transfer.TransferActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class EquipmentInfoActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener, BarcodeScan {


    TextView tbTitleTop, tbTitleBottom, tvEquipmentCode, tvUnitCost;
    String taskType, barcodeID;
    ImageView ivBack, ivTick;
    ToolhawkEquipment toolhawkEquipment;
    Spinner spDepartment, spLocation, spManufacturer, spModel, spCategory;
    List<Department> depList = new ArrayList<Department>();
    List<ETSLocations> locList = new ArrayList<ETSLocations>();
    List<Category> categoryList = new ArrayList<Category>();
    List<Manufacturer> manuList = new ArrayList<Manufacturer>();
    int posDepartment, posManufacturer, posModel, posLoc, posCategory;
    Button btnSearchLoc;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    SharedPreferencesManager sharedPreferencesManager;
    String[] locations;
    ImageView ivAdd;
    RelativeLayout rlAddViewNotes;

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
        rlAddViewNotes = (RelativeLayout) findViewById(R.id.rlAddViewNotes);
        tvEquipmentCode = (TextView) findViewById(R.id.tvEquipmentCode);
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        tvUnitCost = (TextView) findViewById(R.id.tvUnitCost);
        spDepartment = (Spinner) findViewById(R.id.spDepartment);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spLocation = (Spinner) findViewById(R.id.spLocation);
        spManufacturer = (Spinner) findViewById(R.id.spManufacturer);
        spModel = (Spinner) findViewById(R.id.spModel);
        btnSearchLoc = (Button) findViewById(R.id.btnSearchLoc);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        taskType = getIntent().getStringExtra("taskType");
        barcodeID = getIntent().getStringExtra("barcodeID");
        tbTitleTop.setText("Toolhawk");


    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(EquipmentInfoActivity.this);
        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(barcodeID);
        depList = DataManager.getInstance().getAllDepartments();
        locList = DataManager.getInstance().getAllETSLocations();
        categoryList = DataManager.getInstance().getAllCategories();

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


        if (null != categoryList) {
            int sizeCategory = categoryList.size() + 1;
            String[] categories = new String[sizeCategory];
            categories[0] = "Please select a Category";
            for (int i = 0; i < categoryList.size(); i++) {
                categories[i + 1] = categoryList.get(i).getCode();
            }

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            ArrayAdapter<String> dataAdapterDeviceType = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, categories);
            dataAdapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategory.setAdapter(dataAdapterDeviceType);

            if (taskType.startsWith("vie")) {
                for (int i = 0; i < categoryList.size() + 1; i++) {
                    if (null != toolhawkEquipment.getCategory()) {
                        if (toolhawkEquipment.getCategory().getCode().toLowerCase().equals(spCategory.getItemAtPosition(i).toString().toLowerCase())) {
                            spCategory.setSelection(i);
                            posCategory = i;
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


        if (taskType.startsWith("vie")) {


            if (null != toolhawkEquipment.getEquipmentLocationInfo() && null != toolhawkEquipment.getEquipmentLocationInfo().getLocationType() && toolhawkEquipment.getEquipmentLocationInfo().getLocationType().toLowerCase().startsWith("loc")) {
                if (null != locList) {
                    int sizeLocations = locList.size() + 1;
                    locations = new String[sizeLocations];
                    locations[0] = "Please select a location";
                    for (int i = 0; i < locList.size(); i++) {
                        locations[i + 1] = locList.get(i).getCode();
                    }

                    ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, locations);
                    dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spLocation.setAdapter(dataAdapterVendor);


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
            } else if (null != toolhawkEquipment.getEquipmentLocationInfo() && toolhawkEquipment.getEquipmentLocationInfo().getLocationType().toLowerCase().startsWith("job")) {

                if (null != toolhawkEquipment.getEquipmentLocationInfo().getLocation()) {
                    locations = new String[1];
                    locations[0] = toolhawkEquipment.getEquipmentLocationInfo().getLocation();
                    ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, locations);
                    dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spLocation.setAdapter(dataAdapterVendor);
                    spLocation.setSelection(0);
                }
            } else if (null != toolhawkEquipment.getEquipmentLocationInfo() && toolhawkEquipment.getEquipmentLocationInfo().getLocationType().toLowerCase().startsWith("eq")) {
                if (null != toolhawkEquipment.getEquipmentLocationInfo().getLocation()) {
                    locations = new String[1];
                    locations[0] = toolhawkEquipment.getEquipmentLocationInfo().getLocation();
                    ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, locations);
                    dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spLocation.setAdapter(dataAdapterVendor);
                    spLocation.setSelection(0);
                }
            } else {

            }
        } else

        {
            locations = new String[1];
            locations[0] = "Please select a location";
            ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, locations);
            dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spLocation.setAdapter(dataAdapterVendor);
        }


        String[] models = new String[1];
        models[0] = "Please select a model";
        ArrayAdapter<String> dataAdapterModel = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, models);
        dataAdapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModel.setAdapter(dataAdapterModel);


    }


    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivAdd.setOnClickListener(mGlobal_OnClickListener);
        rlAddViewNotes.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
        btnSearchLoc.setOnClickListener(mGlobal_OnClickListener);
        spDepartment.setOnItemSelectedListener(this);
        spCategory.setOnItemSelectedListener(this);
        spLocation.setOnItemSelectedListener(this);
        spManufacturer.setOnItemSelectedListener(this);
        spModel.setOnItemSelectedListener(this);


        spCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

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
            btnSearchLoc.setVisibility(View.GONE);
            rlAddViewNotes.setVisibility(View.VISIBLE);
            tbTitleBottom.setText("Equipment Info");
            tvEquipmentCode.setEnabled(false);
            spDepartment.setEnabled(false);
            spCategory.setEnabled(true);
            spLocation.setEnabled(false);
            tvEquipmentCode.setText("" + toolhawkEquipment.getCode());
            if (null != toolhawkEquipment.getUnitCost()) {
                tvUnitCost.setText("" + toolhawkEquipment.getUnitCost());
            } else {
                tvUnitCost.setText("");
            }

        } else {
            tbTitleBottom.setText("Add Equipment");
            btnSearchLoc.setVisibility(View.VISIBLE);
            spDepartment.setEnabled(true);
            spCategory.setEnabled(true);
            spLocation.setEnabled(true);
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


                case R.id.ivAdd: {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    break;
                }

                case R.id.rlAddViewNotes: {
                    Intent in = new Intent(EquipmentInfoActivity.this, ToolHawkNotesActivity.class);
                    in.putExtra("eqCode", tvEquipmentCode.getText().toString());
                    in.putExtra("taskType", taskType);
                    startActivity(in);
                    break;
                }


                case R.id.btnSearchLoc: {
                    if (0 != posDepartment) {
                        checkCameraPermission();
                    } else {
                        showToast("Please select a Department first!");
                    }
                    break;
                }


                case R.id.ivTick: {

                    ToolhawkEquipment equipment = null;
                    if (checkValidation()) {

                        if (taskType.startsWith("vie")) {
                            ETSLocation etsLocation = null;
                            EquipmentLocationInfo EquipmentLocationInfo = null;
                            ETSLocations etsLoc = DataManager.getInstance().getETSLocationsByCode(spLocation.getItemAtPosition(posLoc).toString());
                            if (null != etsLoc) {
                                etsLocation = new ETSLocation();
                                etsLocation.setCode(etsLoc.getCode());
                                etsLocation.setID(etsLoc.getID());
                                etsLocation.setDescription(etsLoc.getDescription());
                                etsLocation.setCustomerID(etsLoc.getCustomer().getID());
                                etsLocation.setSiteID(etsLoc.getSite().getID());
                                etsLocation.setBuildingID(etsLoc.getBuilding().getID());

                                EquipmentLocationInfo = new EquipmentLocationInfo();
                                EquipmentLocationInfo.setLocationType("Location");
                                EquipmentLocationInfo.setLocation(etsLoc.getCode());
                            }


                            if (null == EquipmentLocationInfo) {
                                if (null != toolhawkEquipment && null != toolhawkEquipment.getEquipmentLocationInfo()) {
                                    EquipmentLocationInfo = toolhawkEquipment.getEquipmentLocationInfo();
                                }
                            }
                            Category category = new Category();

                            if(0!=posCategory){
                                category = categoryList.get(posCategory-1);
                            }
                            equipment = new ToolhawkEquipment(
                                    category,
                                    toolhawkEquipment.getID(),
                                    tvEquipmentCode.getText().toString(),
                                    tvUnitCost.getText().toString(),
                                    DataManager.getInstance().getDepartmentByCode(spDepartment.getItemAtPosition(posDepartment).toString()),
                                    etsLocation,
                                    DataManager.getInstance().getAssetManufacturer(spManufacturer.getItemAtPosition(posManufacturer).toString()),
                                    DataManager.getInstance().getAssetModel(spModel.getItemAtPosition(posModel).toString()),
                                    toolhawkEquipment.isAdded(),
                                    true,
                                    EquipmentLocationInfo
                            );
                            DataManager.getInstance().addToolHawkEquipment(equipment);
                            showToast("Asset Updated!");
                            sendMessage("finish");
                            ivAdd.setVisibility(View.VISIBLE);
                            rlAddViewNotes.setVisibility(View.VISIBLE);
                        } else {
                            ETSLocation etsLocation = new ETSLocation();
                            ToolhawkEquipment eq = DataManager.getInstance().getToolhawkEquipment(tvEquipmentCode.getText().toString());
                            if (null == eq) {
                                ETSLocations etsLoc = DataManager.getInstance().getETSLocationsByCode(spLocation.getItemAtPosition(posLoc).toString());
                                if (null != etsLoc) {
                                    etsLocation.setCode(etsLoc.getCode());
                                    etsLocation.setID(etsLoc.getID());
                                    etsLocation.setDescription(etsLoc.getDescription());
                                    etsLocation.setCustomerID(etsLoc.getCustomer().getID());
                                    etsLocation.setSiteID(etsLoc.getSite().getID());
                                    etsLocation.setBuildingID(etsLoc.getBuilding().getID());


                                }
                                EquipmentLocationInfo EquipmentLocationInfo = new EquipmentLocationInfo();
                                EquipmentLocationInfo.setLocationType("Location");
                                EquipmentLocationInfo.setLocation(etsLoc.getCode());

                                Category category = new Category();

                                if(0!=posCategory){
                                    category = categoryList.get(posCategory-1);
                                }
                                equipment = new ToolhawkEquipment(
                                        category,
                                        0,
                                        tvEquipmentCode.getText().toString(),
                                        tvUnitCost.getText().toString(),
                                        DataManager.getInstance().getDepartmentByCode(spDepartment.getItemAtPosition(posDepartment).toString()),
                                        etsLocation,
                                        DataManager.getInstance().getAssetManufacturer(spManufacturer.getItemAtPosition(posManufacturer).toString()),
                                        DataManager.getInstance().getAssetModel(spModel.getItemAtPosition(posModel).toString()),
                                        true,
                                        false,
                                        EquipmentLocationInfo
                                );
                                DataManager.getInstance().addToolHawkEquipment(equipment);
                                Toast.makeText(EquipmentInfoActivity.this, "Asset Added!", Toast.LENGTH_SHORT).show();
                                showToast("You can add Note(s) for this Asset!");
                                ivAdd.setVisibility(View.VISIBLE);
                                rlAddViewNotes.setVisibility(View.VISIBLE);
                            } else {
                                showToast("Asset with Equipment ID " + tvEquipmentCode.getText().toString() + " Already Exist!");
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
        } else if (taskType.toLowerCase().startsWith("add") && 0 == posDepartment) {
            showToast("Please select a Department");
        } else if (taskType.toLowerCase().startsWith("add") &&
                0 == posLoc) {
            showToast("Please select a Location");
        }  else if (0 == posManufacturer) {
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
                    if (taskType.toLowerCase().startsWith("add")) {
                        locList.clear();
                        if (null != DataManager.getInstance().getDepartmentByCode(strSelectedState)) {
                            locList = DataManager.getInstance().getAllDepETSLocations(DataManager.getInstance().getDepartmentByCode(strSelectedState).getID());
                        }
                        if (null != locList) {
                            int sizeLocations = locList.size() + 1;
                            locations = new String[sizeLocations];
                            locations[0] = "Please select a location";
                            for (int i = 0; i < locList.size(); i++) {
                                locations[i + 1] = locList.get(i).getCode();
                            }

                            ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(EquipmentInfoActivity.this, android.R.layout.simple_spinner_item, locations);
                            dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spLocation.setAdapter(dataAdapterVendor);
                        }
                    }
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
                            if (null != toolhawkEquipment && null != toolhawkEquipment.getModel()) {
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


                if (!taskType.startsWith("vie")) {
                    Model model = DataManager.getInstance().getAssetModel(strSelectedState);
                    if (null != model && null != model.getUnitCost()) {
                        tvUnitCost.setText("" + model.getUnitCost());
                    } else {
                        tvUnitCost.setText("");
                    }
                }


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


            case R.id.spCategory: {
                posCategory = position;
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


    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(EquipmentInfoActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", "searchLoc");
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(EquipmentInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(EquipmentInfoActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(EquipmentInfoActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(EquipmentInfoActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_NEVER_ASK_AGAIN)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(EquipmentInfoActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", EquipmentInfoActivity.this.getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(EquipmentInfoActivity.this, "Go to Permissions to Grant Camera permission", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    //just request the permission
                    ActivityCompat.requestPermissions(EquipmentInfoActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(EquipmentInfoActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", "searchLoc");
                startActivity(in);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sharedPreferencesManager.setBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION, true);
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(EquipmentInfoActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", "searchLoc");
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(EquipmentInfoActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(EquipmentInfoActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(EquipmentInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    sharedPreferencesManager.setBoolean(SharedPreferencesManager.IS_NEVER_ASK_AGAIN, true);
                    Toast.makeText(EquipmentInfoActivity.this, "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void BarcodeScanned(Barcode barcode) {
        String message = barcode.getMessage();
        boolean found = false;
        for (int i = 0; i < locations.length; i++) {
            if (message.toLowerCase().equals(locations[i].toLowerCase())) {
                spLocation.setSelection(i);
                found = true;
            }
        }

        if (!found) {
            showToast("Location not found!");
            spLocation.setSelection(0);
        }
    }
}
