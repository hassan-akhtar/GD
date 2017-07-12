package com.ets.gd.Inventory.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Adapters.AssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Move.LocationSelectionActivity;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Interfaces.MaterialAdded;
import com.ets.gd.Inventory.Adapters.InventoryScannedMaterialAdapter;
import com.ets.gd.Inventory.Move.InventoryScanActivityWithList;
import com.ets.gd.Inventory.Move.MoveFinalActivity;
import com.ets.gd.Inventory.Move.MoveToActivity;
import com.ets.gd.Inventory.Receive.ReceiveMaterialActivity;
import com.ets.gd.Models.Barcode;
import com.ets.gd.Models.Note;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.Material;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.ToolhawkScanActivityWithList;
import com.ets.gd.ToolHawk.Fragments.ToolhawkDashboardFragmentNew;
import com.ets.gd.ToolHawk.Move.MoveAssetActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveMaterialScanListActivity extends AppCompatActivity implements BarcodeScan, MaterialAdded {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText;
    Button btnCross, btnNewCount, btnExistingCount, btnScan, btnLocation, btnAsset;
    LinearLayout llbtns, llbtnsEq;
    EditText etBarcode;
    ImageView ivInfo;
    String taskType, JobNumber, materialID, quantity;
    int JobNumberID, materialLocID, eqID, inventoryID;
    ImageView ivBack, ivTick;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    SharedPreferencesManager sharedPreferencesManager;
    ToolhawkEquipment toolhawkEquipment;
    ETSLocation etsLocation;
    RelativeLayout rlBottomSheetMove, rlForwardArrow, rlJobNumber;
    RecyclerView rvList;
    TextView tvCount, tvCountSupportText, tvTaskName, tvJobNumber;
    InventoryScannedMaterialAdapter mAdapter;
    public static List<com.ets.gd.Models.Material> materialList = new ArrayList<com.ets.gd.Models.Material>();
    Context mContext;
    String[] locationNames;
    public static boolean addMoreMaretailItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_scan);

        initViews();
        initObj();
        initListeners();
        setupView();
        hideKeyboard();

    }


    private void initViews() {

        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        tvJobNumber = (TextView) findViewById(R.id.tvJobNumber);
        rlJobNumber = (RelativeLayout) findViewById(R.id.rlJobNumber);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCountSupportText = (TextView) findViewById(R.id.tvCountSupportText);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnNewCount = (Button) findViewById(R.id.btnNewCount);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        btnAsset = (Button) findViewById(R.id.btnAsset);
        btnExistingCount = (Button) findViewById(R.id.btnExistingCount);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtnsQuickCount);
        rlBottomSheetMove = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);
        rlForwardArrow = (RelativeLayout) findViewById(R.id.rlForwardArrow);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        llbtnsEq = (LinearLayout) findViewById(R.id.llbtnsEq);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        quantity = getIntent().getStringExtra("quantity");
        JobNumberID = getIntent().getIntExtra("JobNumberID", 0);
        materialLocID = getIntent().getIntExtra("materialLocID", 0);
        inventoryID = getIntent().getIntExtra("inventoryID", 0);
        materialID = getIntent().getStringExtra("materialID");
        JobNumber = getIntent().getStringExtra("JobNumber");
        eqID = getIntent().getIntExtra("eqID", 0);
        tbTitleTop.setText("Inventory");
        tbTitleBottom.setText("" + taskType);
        tvUnderText.setText("Scan/Enter ID of Material");


        if (null != JobNumber) {
            rlJobNumber.setVisibility(View.VISIBLE);
            tvJobNumber.setText("" + JobNumber);
        } else {
            rlJobNumber.setVisibility(View.GONE);
        }
        rlJobNumber.setVisibility(View.GONE);
    }

    private void initObj() {
        mContext = this;
        sharedPreferencesManager = new SharedPreferencesManager(MoveMaterialScanListActivity.this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));

        com.ets.gd.Models.Material material = new com.ets.gd.Models.Material();
        material.setName("" + materialID);
        material.setQuantity("" + quantity);
        material.setLocID(materialLocID);
        material.setEquipmentID(eqID);
        material.setInventoryID(inventoryID);
        materialList.add(material);
        ReceiveMaterialActivity.materialList = materialList;
        mAdapter = new InventoryScannedMaterialAdapter(MoveMaterialScanListActivity.this, materialList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MoveMaterialScanListActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        if (0 != materialList.size()) {
            rlBottomSheetMove.setVisibility(View.VISIBLE);
            if (taskType.toLowerCase().startsWith("mo")) {
                tvTaskName.setText("MOVE ASSET");
                tvCount.setText("" + materialList.size());
                tvCountSupportText.setText("Asset(s) Selected to Move");
            } else if (taskType.toLowerCase().startsWith("iss")) {
                tvTaskName.setText("ISSUE ASSET");
                tvCount.setText("" + materialList.size());
                tvCountSupportText.setText("Asset(s) Selected to Issue");
            } else if (taskType.toLowerCase().startsWith("rec")) {
                tvTaskName.setText("RECEIVE ASSET");
                tvCount.setText("" + materialList.size());
                tvCountSupportText.setText("Asset(s) Selected to Receive");
            }
        }
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnNewCount.setOnClickListener(mGlobal_OnClickListener);
        btnExistingCount.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);


        rvList.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(MoveMaterialScanListActivity.this, rvList, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);
                alertDialogBuilder.setTitle("Remove Material");
                alertDialogBuilder.setMessage("Are you sure you want to remove " + materialList.get(position).getName());
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("REMOVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        mAdapter.removeAt(position);
                                        if (0 != materialList.size()) {
                                            tvCount.setText("" + materialList.size());
                                        } else {
                                            rlBottomSheetMove.setVisibility(View.GONE);
                                        }

                                    }
                                })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                alertDialogBuilder.show();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);


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
                    if (tbTitleBottom.getText().toString().toLowerCase().startsWith("mo")) {


                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {

                            Material material = DataManager.getInstance().getMaterial(etBarcode.getText().toString());
                            if (null != material) {
                                setupFlags();
                                sendMessage("finish");
                                Intent in = new Intent(MoveMaterialScanListActivity.this, MaterialQuantityActivity.class);
                                in.putExtra("taskType", taskType);
                                in.putExtra("materialID", etBarcode.getText().toString());
                                startActivity(in);
                                etBarcode.setText("");
                            } else {
                                showToast("No Material found!");
                            }
                        }

                    } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("iss")) {


                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {
                            Material material = DataManager.getInstance().getMaterial(etBarcode.getText().toString());
                            if (null != material) {
                                setupFlags();
                                sendMessage("finish");
                                Intent in = new Intent(MoveMaterialScanListActivity.this, MaterialQuantityActivity.class);
                                in.putExtra("taskType", taskType);
                                in.putExtra("materialID", etBarcode.getText().toString());
                                startActivity(in);
                                etBarcode.setText("");
                            } else {
                                showToast("No Material found!");
                            }
                        }
                    } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("rec")) {

                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {
                            Material material = DataManager.getInstance().getMaterial(etBarcode.getText().toString());
                            if (null != material) {
                                setupFlags();
                                sendMessage("finish");
                                Intent in = new Intent(MoveMaterialScanListActivity.this, MaterialQuantityActivity.class);
                                in.putExtra("taskType", taskType);
                                in.putExtra("materialID", etBarcode.getText().toString());
                                startActivity(in);
                                etBarcode.setText("");
                            } else {
                                showToast("No Material found!");
                            }
                        }
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


                case R.id.rlForwardArrow: {

                    if (taskType.toLowerCase().startsWith("mo") || taskType.toLowerCase().startsWith("iss")) {
                        locationNames = new String[materialList.size()];
                        for (int i = 0; i < materialList.size(); i++) {
                            if (null != DataManager.getInstance().getETSLocationByIDOnly(materialList.get(i).getLocID())) {
                                locationNames[i] = DataManager.getInstance().getETSLocationByIDOnly(materialList.get(i).getLocID()).getCode();
                            } else if (null != DataManager.getInstance().getToolhawkEquipmentByID(materialList.get(i).getLocID())) {
                                locationNames[i] = DataManager.getInstance().getToolhawkEquipmentByID(materialList.get(i).getLocID()).getCode();
                            } else {
                                locationNames[i] = "N/A";
                            }
                        }

                        Set<String> uniqueWords = new HashSet<String>(Arrays.asList(locationNames));
                        locationNames = new String[uniqueWords.size()];
                        int j = 0;
                        for (String loc : uniqueWords) {
                            locationNames[j] = loc;
                            j++;
                        }
                        MoveFinalActivity.locationNames = locationNames;

                        Intent in = new Intent(MoveMaterialScanListActivity.this, MoveToActivity.class);
                        MoveFinalActivity.materialList = materialList;
                        in.putExtra("taskType", taskType);
                        in.putExtra("materialName", materialID);
                        if (1 < materialList.size()) {
                            in.putExtra("isMultiple", true);
                        } else {
                            in.putExtra("isMultiple", false);
                        }
                        startActivity(in);
                    } else {
                        Intent in = new Intent(MoveMaterialScanListActivity.this, InventoryScanActivityWithList.class);
                        in.putExtra("taskType", taskType);
                        in.putExtra("scanType", "Location");
                        in.putExtra("material", materialID);
                        if (1 < materialList.size()) {
                            in.putExtra("isMultiple", true);
                        } else {
                            in.putExtra("isMultiple", false);
                        }
                        startActivity(in);
                    }
                    break;
                }

            }
        }

    };

    private void setupFlags() {
        addMoreMaretailItem = true;
        InventoryJobNumberActivity.materialAdded = this;
        MaterialQuantityActivity.materialAdded = this;
    }


    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("move-complete");
        intent.putExtra("message", msg);
        intent.putExtra("type", "fin");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private final BroadcastReceiver mMoveCompleteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            String type = intent.getStringExtra("type");

            if (!"fin".equals(type)) {
                if (message.startsWith("fin")) {
                    finish();
                }
            }

        }
    };

    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(MoveMaterialScanListActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(MoveMaterialScanListActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MoveMaterialScanListActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoveMaterialScanListActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(MoveMaterialScanListActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoveMaterialScanListActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getBaseContext(), "Go to Permissions to Grant Camera permission", Toast.LENGTH_LONG).show();
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
                    ActivityCompat.requestPermissions(MoveMaterialScanListActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(MoveMaterialScanListActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
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
                Intent in = new Intent(MoveMaterialScanListActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MoveMaterialScanListActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoveMaterialScanListActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(MoveMaterialScanListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean checkValidation() {
        return false;
    }

    @Override
    public void BarcodeScanned(Barcode barcode) {
        String message = barcode.getMessage();
        String task = barcode.getTask();

        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(message);

        if (tbTitleBottom.getText().toString().toLowerCase().startsWith("mo")) {

            Material material = DataManager.getInstance().getMaterial(message);
            if (null != material) {
                setupFlags();
                sendMessage("finish");
                Intent in = new Intent(MoveMaterialScanListActivity.this, MaterialQuantityActivity.class);
                in.putExtra("taskType", taskType);
                in.putExtra("materialID", message);
                startActivity(in);
                etBarcode.setText("");
            } else {
                showToast("No Material found!");
            }

        } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("iss")) {
            Material material = DataManager.getInstance().getMaterial(message);
            if (null != material) {
                setupFlags();
                sendMessage("finish");
                Intent in = new Intent(MoveMaterialScanListActivity.this, MaterialQuantityActivity.class);
                in.putExtra("taskType", taskType);
                in.putExtra("materialID", message);
                startActivity(in);
                etBarcode.setText("");
            } else {
                showToast("No Material found!");
            }

        } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("rec")) {
            Material material = DataManager.getInstance().getMaterial(message);
            if (null != material) {
                setupFlags();
                sendMessage("finish");
                Intent in = new Intent(MoveMaterialScanListActivity.this, MaterialQuantityActivity.class);
                in.putExtra("taskType", taskType);
                in.putExtra("materialID", message);
                startActivity(in);
                etBarcode.setText("");
            } else {
                showToast("No Material found!");
            }

        }
    }

    @Override
    public void MaterialMoveListItemAdded(com.ets.gd.Models.Material material) {
        boolean isAdded = false;
        for (com.ets.gd.Models.Material mat : materialList) {
            if (mat.getName().toLowerCase().equals(material.getName().toLowerCase()) && mat.getLocID()==material.getLocID()) {
                int newQuantity = Integer.parseInt(mat.getQuantity()) + Integer.parseInt(material.getQuantity());
                mat.setQuantity(String.valueOf(newQuantity));
                isAdded = true;
                break;
            }
        }
        if (!isAdded) {
            materialList.add(material);
        }
        ReceiveMaterialActivity.materialList = materialList;
        tvCount.setText("" + materialList.size());
        mAdapter.notifyDataSetChanged();
    }
}
