package com.ets.gd.Inventory.Move;

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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Inventory.Adapters.UserContainerAdapter;
import com.ets.gd.Inventory.Receive.ReceiveMaterialActivity;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.ResponseDTOs.Building;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber;
import com.ets.gd.NetworkLayer.ResponseDTOs.MobileUser;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Adapters.MoveAdapter;
import com.ets.gd.ToolHawk.Move.MoveAssetActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class InventoryScanActivityWithList extends AppCompatActivity implements BarcodeScan {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvScanType, textDepartment;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    UserContainerAdapter mAdapter;
    String taskType, scanType, material;
    ImageView ivBack, ivTick;
    RecyclerView rvList;
    private List<ToolhawkEquipment> containerList = new ArrayList<ToolhawkEquipment>();
    private List<ETSLocations> locList = new ArrayList<ETSLocations>();
    private List<MobileUser> userList = new ArrayList<MobileUser>();
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    SharedPreferencesManager sharedPreferencesManager;
    ETSLocations etsLocations;
    ToolhawkEquipment container;
    ETSLocations etsLocation;
    MobileUser mobileUser;
    boolean isMultiple;
    int JobNumberID;

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
        llbtns = (LinearLayout) findViewById(R.id.llbtnsQuickCount);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        textDepartment = (TextView) findViewById(R.id.textDepartment);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvScanType = (TextView) findViewById(R.id.tvScanType);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        scanType = getIntent().getStringExtra("scanType");
        material = getIntent().getStringExtra("material");
        isMultiple = getIntent().getBooleanExtra("isMultiple", false);
        JobNumberID = getIntent().getIntExtra("JobNumberID",0);
        tbTitleTop.setText("Inventory");
        tbTitleBottom.setText("" + taskType);
        tvScanType.setText("Select / Scan " + scanType);

        if (isMultiple) {
            tvDepartment.setText("" + material + ",...");
        } else {
            tvDepartment.setText(" " + material);
        }
        textDepartment.setText("Material:");
        tvUnderText.setText("Scan or Enter " + scanType + " ID");
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(InventoryScanActivityWithList.this);
        hideKeyboard();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);

        rvList.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(InventoryScanActivityWithList.this, rvList, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (scanType.toLowerCase().startsWith("loc")) {
                    if (!taskType.toLowerCase().startsWith("rec")) {
                        Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                        in.putExtra("taskType", taskType);
                        in.putExtra("scanType", scanType);
                        in.putExtra("JobNumberID", JobNumberID);
                        in.putExtra("toLoc", locList.get(position).getCode());
                        startActivity(in);
                    } else {
                        Intent in = new Intent(InventoryScanActivityWithList.this, ReceiveMaterialActivity.class);
                        in.putExtra("toLoc", locList.get(position).getCode());
                        in.putExtra("JobNumberID", JobNumberID);
                        startActivity(in);
                    }

                } else if (scanType.toLowerCase().startsWith("con")) {
                    Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                    in.putExtra("taskType", taskType);
                    in.putExtra("scanType", scanType);
                    in.putExtra("JobNumberID", JobNumberID);
                    in.putExtra("toLoc", containerList.get(position).getCode());
                    startActivity(in);

                } else if (scanType.toLowerCase().startsWith("use")) {
                    Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                    in.putExtra("taskType", taskType);
                    in.putExtra("scanType", scanType);
                    in.putExtra("JobNumberID", JobNumberID);
                    in.putExtra("toLoc", userList.get(position).getUserName());
                    startActivity(in);

                }


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

        if (scanType.toLowerCase().startsWith("loc")) {

            locList = DataManager.getInstance().getAllETSLocations();
            mAdapter = new UserContainerAdapter(InventoryScanActivityWithList.this, locList, "loc");

        } else if (scanType.toLowerCase().startsWith("con")) {
            containerList = DataManager.getInstance().getAllContainerToolhawkAssets();
            mAdapter = new UserContainerAdapter(InventoryScanActivityWithList.this, "container", containerList);
        } else if (scanType.toLowerCase().startsWith("use")) {
            userList = DataManager.getInstance().getAllMobileUserList();
            mAdapter = new UserContainerAdapter("use", InventoryScanActivityWithList.this, userList);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(InventoryScanActivityWithList.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {

                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();
                    } else {

                        if (scanType.toLowerCase().startsWith("loc")) {
                            etsLocations = DataManager.getInstance().getETSLocationsByCode(etBarcode.getText().toString());

                            if (null != etsLocations) {
                                if (!taskType.toLowerCase().startsWith("rec")) {
                                    Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                                    in.putExtra("taskType", taskType);
                                    in.putExtra("scanType", scanType);
                                    in.putExtra("jobNumber", "");
                                    in.putExtra("toLoc", etsLocations.getCode());
                                    startActivity(in);
                                } else {
                                    Intent in = new Intent(InventoryScanActivityWithList.this, ReceiveMaterialActivity.class);
                                    in.putExtra("toLoc", etsLocations.getCode());
                                    startActivity(in);
                                }
                                etBarcode.setText("");
                            } else {
                                showToast("No such location Found!");
                            }


                        } else if (scanType.toLowerCase().startsWith("con")) {
                            container = DataManager.getInstance().getToolhawkContainerEquipment(etBarcode.getText().toString());
                            if (null != container) {
                                Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                                in.putExtra("taskType", taskType);
                                in.putExtra("scanType", scanType);
                                in.putExtra("jobNumber", "");
                                in.putExtra("toLoc", container.getCode());
                                startActivity(in);
                                etBarcode.setText("");
                            } else {
                                showToast("No such Container Found!");
                            }

                        } else if (scanType.toLowerCase().startsWith("use")) {
                            mobileUser = DataManager.getInstance().getMobileUser(etBarcode.getText().toString());
                            if (null != mobileUser) {
                                Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                                in.putExtra("taskType", taskType);
                                in.putExtra("scanType", scanType);
                                in.putExtra("jobNumber", "");
                                in.putExtra("toLoc", mobileUser.getUserName());
                                startActivity(in);
                                etBarcode.setText("");
                            } else {
                                showToast("No User Found!");
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
            }
        }

    };


    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(InventoryScanActivityWithList.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(InventoryScanActivityWithList.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(InventoryScanActivityWithList.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScanActivityWithList.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(InventoryScanActivityWithList.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScanActivityWithList.this);
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
                    ActivityCompat.requestPermissions(InventoryScanActivityWithList.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(InventoryScanActivityWithList.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(InventoryScanActivityWithList.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(InventoryScanActivityWithList.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryScanActivityWithList.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(InventoryScanActivityWithList.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        return false;
    }


    private final BroadcastReceiver mMoveCompleteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");

            if (message.startsWith("fin")) {
                finish();
            }

        }
    };

    @Override
    public void BarcodeScanned(Barcode barcode) {

        String message = barcode.getMessage();
        String task = barcode.getTask();


        if (scanType.toLowerCase().startsWith("loc")) {
            etsLocations = DataManager.getInstance().getETSLocationsByCode(message);

            if (null != etsLocations) {
                if (!taskType.toLowerCase().startsWith("rec")) {
                    Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                    in.putExtra("taskType", taskType);
                    in.putExtra("scanType", scanType);
                    in.putExtra("jobNumber", "");
                    in.putExtra("toLoc", etsLocations.getCode());
                    startActivity(in);
                    etBarcode.setText("");
                } else {
                    Intent in = new Intent(InventoryScanActivityWithList.this, ReceiveMaterialActivity.class);
                    in.putExtra("toLoc", etsLocations.getCode());
                    startActivity(in);
                }
            } else {
                showToast("No such location Found!");
            }

        } else if (scanType.toLowerCase().startsWith("con")) {

            container = DataManager.getInstance().getToolhawkContainerEquipment(message);
            if (null != container) {
                Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                in.putExtra("taskType", taskType);
                in.putExtra("scanType", scanType);
                in.putExtra("jobNumber", "");
                in.putExtra("toLoc", container.getCode());
                startActivity(in);
                etBarcode.setText("");
            } else {
                showToast("No such Container Found!");
            }
        } else if (scanType.toLowerCase().startsWith("use")) {
            mobileUser = DataManager.getInstance().getMobileUser(message);
            if (null != mobileUser) {
                Intent in = new Intent(InventoryScanActivityWithList.this, MoveFinalActivity.class);
                in.putExtra("taskType", taskType);
                in.putExtra("scanType", scanType);
                in.putExtra("jobNumber", "");
                in.putExtra("toLoc", mobileUser.getUserName());
                startActivity(in);
                etBarcode.setText("");
            } else {
                showToast("No User Found!");
            }

        }

    }
}

