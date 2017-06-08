package com.ets.gd.ToolHawk.Move;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.RequestDTOs.ToolhawkMove;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Adapters.ScannedAssetsToolhawkAdapter;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveAssetActivity extends AppCompatActivity implements BarcodeScan {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvMoveType, tvMoveCode, tvFromLoc, tvAssetsNames, tvToLoc, tvMovingAsset;
    Button btnCross, btnScan, btnViewAllLocations, btnViewAllAssets;
    LinearLayout llbtns;
    EditText etBarcode;
    TextView tvCount, tvCountSupportText, tvTaskName;
    RelativeLayout rlForwardArrow, rlMove, rlBottomSheetMove, rlScanArea;
    ImageView ivInfo;
    String taskType, scanType, department, moveCode;
    ImageView ivBack, ivTick;
    boolean isfinalMove = false;
    RecyclerView rvList;
    ScannedAssetsToolhawkAdapter mAdapter;
    private List<ToolhawkEquipment> equipmentList = new ArrayList<ToolhawkEquipment>();
    Context mContext;
    ToolhawkEquipment toolhawkEquipment;
    SharedPreferencesManager sharedPreferencesManager;
    private List<ETSLocations> etsLocationsList = new ArrayList<ETSLocations>();
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] assetNames;
    String[] locationNames, locations;
    private List<ToolhawkMove> equipmentToMoveList = new ArrayList<ToolhawkMove>();

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
        llbtns = (LinearLayout) findViewById(R.id.llbtnsQuickCount);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvMovingAsset = (TextView) findViewById(R.id.tvMovingAsset);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvMoveType = (TextView) findViewById(R.id.tvReturningUser);
        tvMoveCode = (TextView) findViewById(R.id.tvMoveCode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvFromLoc = (TextView) findViewById(R.id.tvFromLoc);
        tvAssetsNames = (TextView) findViewById(R.id.tvAssetsNames);
        tvToLoc = (TextView) findViewById(R.id.tvToLoc);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        rlBottomSheetMove = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);
        rlScanArea = (RelativeLayout) findViewById(R.id.rlScanArea);
        btnViewAllLocations = (Button) findViewById(R.id.btnViewAllLocations);
        btnViewAllAssets = (Button) findViewById(R.id.btnViewAllAssets);
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
        tvUnderText.setText("Scan or Enter Asset ID");
        tvMoveType.setText("Move to " + scanType);
        tvMoveCode.setText("" + moveCode);


        tvCountSupportText.setText("Asset Selected To " + tbTitleBottom.getText().toString());
        tvTaskName.setText(" " + tbTitleBottom.getText().toString().toUpperCase());

        mAdapter = new ScannedAssetsToolhawkAdapter(getApplicationContext(), equipmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);


    }

    private void initObj() {
        mContext = this;
        sharedPreferencesManager = new SharedPreferencesManager(MoveAssetActivity.this);
        hideKeyboard();
    }


    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                etBarcode.getWindowToken(), 0);
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        btnViewAllLocations.setOnClickListener(mGlobal_OnClickListener);
        btnViewAllAssets.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);

        rvList.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(MoveAssetActivity.this, rvList, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);
                alertDialogBuilder.setTitle("Remove Asset");
                alertDialogBuilder.setMessage("Are you sure you want to remove " + equipmentList.get(position).getCode());
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("REMOVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        mAdapter.removeAt(position);
                                        if (0 != equipmentList.size()) {
                                            tvCount.setText("" + equipmentList.size());
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
        llbtns.setVisibility(View.GONE);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {
                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();
                    } else {
                        hideKeyboard();
                        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(etBarcode.getText().toString());
                        if (null != toolhawkEquipment) {
                            if (!equipmentList.contains(toolhawkEquipment)) {


                                if (!scanType.toLowerCase().startsWith("asse")) {
                                    if (department.equals(toolhawkEquipment.getDepartment().getCode())) {
                                        equipmentList.add(DataManager.getInstance().getToolhawkEquipment(etBarcode.getText().toString()));
                                        tvCount.setText("" + equipmentList.size());
                                        rlBottomSheetMove.setVisibility(View.VISIBLE);
                                        mAdapter.notifyDataSetChanged();
                                        etBarcode.setText("");
                                    } else {
                                        showToast("This Asset is not in " + department);
                                    }
                                } else {
                                    if (!toolhawkEquipment.getCode().equals(moveCode)) {
                                        if (department.equals(toolhawkEquipment.getDepartment().getCode()) ) {
                                            equipmentList.add(DataManager.getInstance().getToolhawkEquipment(etBarcode.getText().toString()));
                                            tvCount.setText("" + equipmentList.size());
                                            rlBottomSheetMove.setVisibility(View.VISIBLE);
                                            mAdapter.notifyDataSetChanged();
                                            etBarcode.setText("");
                                        } else {
                                            showToast("This Asset is not in " + department);
                                        }
                                    } else {
                                        showToast("Can not Move to same Asset!" );
                                        showToast("Please select any other asset!" );
                                    }
                                }
                            } else {
                                showToast("Asset Already Added!!");
                            }
                        } else {
                            showToast("Asset not found!");
                        }


                    }
                    break;
                }

                case R.id.ivBack: {
                    finish();
                    break;
                }


                case R.id.btnViewAllAssets: {
                    showAssetList();
                    break;
                }


                case R.id.btnViewAllLocations: {
                    showLocationsList();
                    break;
                }


                case R.id.rlForwardArrow: {
                    if (!isfinalMove) {
                        rvList.setVisibility(View.GONE);
                        rlMove.setVisibility(View.VISIBLE);
                        rlScanArea.setVisibility(View.GONE);
                        isfinalMove = true;

                        assetNames = new String[equipmentList.size()];

                        for (int i = 0; i < equipmentList.size(); i++) {
                            assetNames[i] = equipmentList.get(i).getCode();
                        }

                        tvMovingAsset.setText("Moving " + equipmentList.size() + " asset(s)");
                        tvToLoc.setText("" + moveCode);

                        int locSize = 0;

                        for (ToolhawkEquipment eq : equipmentList) {

                            if (null != eq.getEquipmentLocationInfo() && null != eq.getEquipmentLocationInfo().getLocation()) {
                                locSize = locSize + 1;
                            }
                        }

                        locations = new String[locSize];
                        for (int i = 0; i < locSize; i++) {
                            if (null != equipmentList.get(i).getEquipmentLocationInfo()) {
                                locations[i] = equipmentList.get(i).getEquipmentLocationInfo().getLocation();
                            }
                        }

                        Set<String> uniqueWords = new HashSet<String>(Arrays.asList(locations));
                        locationNames = new String[uniqueWords.size()];

                        int j = 0;
                        for (String loc : uniqueWords) {
                            if (null != loc) {
                                locationNames[j] = loc;
                                j++;
                            }
                        }


                        if (1 < equipmentList.size()) {
                            btnViewAllAssets.setVisibility(View.VISIBLE);
                            tvAssetsNames.setText(equipmentList.get(0).getCode() + ",...");
                        } else {
                            btnViewAllAssets.setVisibility(View.GONE);
                            tvAssetsNames.setText(equipmentList.get(0).getCode());
                        }


                        if (1 < locationNames.length) {
                            btnViewAllLocations.setVisibility(View.VISIBLE);
                            if (null != locationNames[0]) {
                                tvFromLoc.setText(locationNames[0] + "...");
                            } else {
                                tvFromLoc.setText("N/A");
                            }
                        } else {
                            btnViewAllLocations.setVisibility(View.GONE);
                            if (0 != locationNames.length && null != locationNames[0]) {
                                tvFromLoc.setText("" + locationNames[0]);
                            } else {
                                tvFromLoc.setText("N/A");
                            }
                        }


                    } else {

                        new AlertDialog.Builder(MoveAssetActivity.this)
                                .setTitle("Move")
                                .setMessage("Are you sure you want to move " + equipmentList.size() + " asset(s) to " + moveCode + " ?")
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        for (int i = 0; i < equipmentList.size(); i++) {
                                            ToolhawkMove toolhawkMove = new ToolhawkMove();
                                            toolhawkMove.setEquipmentID(equipmentList.get(i).getID());

                                            if (null != DataManager.getInstance().getToolhawkEquipment(moveCode)) {
                                                toolhawkMove.setToEquipmentID(DataManager.getInstance().getToolhawkEquipment(moveCode).getID());
                                                toolhawkMove.setMoveType("Equipment");
                                            } else {
                                                toolhawkMove.setToEquipmentID(0);
                                            }
                                            if (null != DataManager.getInstance().getETSLocationByCode(moveCode)) {
                                                toolhawkMove.setToLocationID(DataManager.getInstance().getETSLocationByCode(moveCode).getID());
                                                toolhawkMove.setMoveType("Location");
                                            } else {
                                                toolhawkMove.setToLocationID(0);
                                            }
                                            if (null != DataManager.getInstance().getJobNumber(moveCode)) {
                                                toolhawkMove.setToJobNumberID(DataManager.getInstance().getJobNumber(moveCode).getID());
                                                toolhawkMove.setMoveType("JobNumber");
                                            } else {
                                                toolhawkMove.setToJobNumberID(0);
                                            }
                                            toolhawkMove.setUserID(0);
                                            equipmentToMoveList.add(toolhawkMove);
                                        }

                                        DataManager.getInstance().saveSyncToolhawkMoveData(equipmentToMoveList);
                                        showToast("Move Complete!");
                                        sendMessage("finish");
                                        finish();
                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .show();

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

    void showAssetList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, assetNames);


        LayoutInflater li = LayoutInflater.from(MoveAssetActivity.this);
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MoveAssetActivity.this);
        alertDialogBuilder.setTitle("Assets");
        alertDialogBuilder.setView(dialogView);
        final ListView listAssets = (ListView) dialogView
                .findViewById(R.id.lvAssets);
        listAssets.setAdapter(adapter);
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    void showLocationsList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, locationNames);


        LayoutInflater li = LayoutInflater.from(MoveAssetActivity.this);
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MoveAssetActivity.this);
        alertDialogBuilder.setTitle("Locations");
        alertDialogBuilder.setView(dialogView);
        final ListView listAssets = (ListView) dialogView
                .findViewById(R.id.lvAssets);
        listAssets.setAdapter(adapter);
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(MoveAssetActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(MoveAssetActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MoveAssetActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoveAssetActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(MoveAssetActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoveAssetActivity.this);
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
                    ActivityCompat.requestPermissions(MoveAssetActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(MoveAssetActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(MoveAssetActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MoveAssetActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoveAssetActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(MoveAssetActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

    @Override
    public void BarcodeScanned(Barcode barcode) {
        String message = barcode.getMessage();
        String task = barcode.getTask();


        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(message);


        if (null != toolhawkEquipment) {
            if (!equipmentList.contains(toolhawkEquipment)) {
                if (!toolhawkEquipment.getCode().equals(moveCode)) {
                if (department.equals(toolhawkEquipment.getDepartment().getCode())) {
                    equipmentList.add(DataManager.getInstance().getToolhawkEquipment(message));
                    tvCount.setText("" + equipmentList.size());
                    rlBottomSheetMove.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                } else {
                    showToast("This Asset is not in " + department);
                }}else{
                    showToast("Can not Move to same Asset!" );
                    showToast("Please select any other asset!" );
                }
            } else {
                showToast("Asset Already Added!!");
            }
        } else {
            showToast("Asset not found!");
        }

    }
}


