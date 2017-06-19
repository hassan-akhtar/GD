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
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Inventory.Adapters.InventoryScannedMaterialAdapter;
import com.ets.gd.Models.Barcode;
import com.ets.gd.Models.Note;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Fragments.ToolhawkDashboardFragmentNew;
import com.ets.gd.ToolHawk.Move.MoveAssetActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class MoveMaterialScanListActivity extends AppCompatActivity implements BarcodeScan {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText;
    Button btnCross, btnNewCount, btnExistingCount, btnScan, btnLocation, btnAsset;
    LinearLayout llbtns, llbtnsEq;
    EditText etBarcode;
    ImageView ivInfo;
    String taskType, JobNumber, materialID, quantity;
    int JobNumberID, materialLocID;
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
    private List<Note> materialList = new ArrayList<Note>();
    Context mContext;

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
        rlJobNumber  = (RelativeLayout) findViewById(R.id.rlJobNumber);
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
        materialID = getIntent().getStringExtra("materialID");
        JobNumber = getIntent().getStringExtra("JobNumber");
        tbTitleTop.setText("Inventory");
        tbTitleBottom.setText("" + taskType);
        tvUnderText.setText("Scan/Enter ID of Material");


        if(null!=JobNumber){
            rlJobNumber.setVisibility(View.VISIBLE);
            tvJobNumber.setText(""+JobNumber);
        }else{
            rlJobNumber.setVisibility(View.GONE);
        }
    }

    private void initObj() {
        mContext =this;
        sharedPreferencesManager = new SharedPreferencesManager(MoveMaterialScanListActivity.this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));

        Note material = new Note();
        material.setNoteTitle("" + materialID);
        material.setNoteDescription("" + quantity);
        materialList.add(material);
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
                tvCountSupportText.setText("Asset Selected to Move");
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
                alertDialogBuilder.setMessage("Are you sure you want to remove " + materialList.get(position).getNoteTitle());
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
                            sendMessage("finish");
                            Intent in = new Intent(MoveMaterialScanListActivity.this, CommonMaterialScanActivity.class);
                            in.putExtra("taskType", taskType);
                            startActivity(in);
                            finish();
                        }

                    } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("iss")) {


                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {

                        }
                    } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("rec")) {

                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {

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
                    showToast("yooo");
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

    private final BroadcastReceiver mMoveCompleteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");

            if (message.startsWith("fin")) {
                finish();
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

        } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("iss")) {

        } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("rec")) {

        }
    }
}
