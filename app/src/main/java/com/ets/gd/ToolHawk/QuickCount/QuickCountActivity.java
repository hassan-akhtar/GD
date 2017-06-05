package com.ets.gd.ToolHawk.QuickCount;

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
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.ets.gd.Models.Barcode;
import com.ets.gd.Models.Department;
import com.ets.gd.Models.ToolhawkAsset;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.CommonToolhawkDepartmentActivity;
import com.ets.gd.ToolHawk.Activities.ToolhawkScanActivityWithList;
import com.ets.gd.ToolHawk.Adapters.DepartmentAdapter;
import com.ets.gd.ToolHawk.Adapters.QuickCountAdapter;
import com.ets.gd.ToolHawk.Move.MoveActivity;
import com.ets.gd.ToolHawk.Move.MoveAssetActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class QuickCountActivity extends AppCompatActivity implements BarcodeScan {


    TextView tbTitleTop, tbTitleBottom, tvAssetOtherInfo, tvLocName, tvBarcodeTitle, tvUnderText, tvBarcodeValue;
    TextView tvUnExpected, tvExpected, tvFound;
    String taskType, locationCode;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    ImageView ivBack, ivTick;
    RecyclerView rvQuickCount;
    QuickCountAdapter mAdapter;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    SharedPreferencesManager sharedPreferencesManager;
    int assetsFound = 0, unexpectedAssets = 0;
    private List<ToolhawkEquipment> assetList = new ArrayList<ToolhawkEquipment>();
    ETSLocations etsLocation;
    ToolhawkEquipment toolhawkEquipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_count);

        initViews();
        initObj();
        initListeners();
        setupView();
        hideKeyboard();
        setupAssetList();

    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvAssetOtherInfo = (TextView) findViewById(R.id.tvAssetOtherInfo);
        tvUnExpected = (TextView) findViewById(R.id.tvUnExpected);
        tvExpected = (TextView) findViewById(R.id.tvExpected);
        tvFound = (TextView) findViewById(R.id.tvFound);
        tvLocName = (TextView) findViewById(R.id.tvLocName);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        rvQuickCount = (RecyclerView) findViewById(R.id.rvQuickCount);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtns);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);

        taskType = getIntent().getStringExtra("taskType");
        locationCode = getIntent().getStringExtra("locationCode");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("Quick Count");

        etsLocation = DataManager.getInstance().getETSLocationsByCode(locationCode);

        if (null != etsLocation) {
            tvLocName.setText("" + etsLocation.getCode());
            tvAssetOtherInfo.setText("" + etsLocation.getDescription());
        }

    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(QuickCountActivity.this);
    }


    private void initListeners() {
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);

        rvQuickCount.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(QuickCountActivity.this, rvQuickCount, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // showToast("" + assetList.get(position).getCode());
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
        ivInfo.setVisibility(View.VISIBLE);
        tvUnderText.setVisibility(View.VISIBLE);
        llbtns.setVisibility(View.GONE);
        tvBarcodeValue.setText("");
        etBarcode.setVisibility(View.VISIBLE);
        etBarcode.setText("");
        btnCross.setVisibility(View.GONE);
    }


    private void setupAssetList() {
/*        ToolhawkAsset asset = new ToolhawkAsset();
        asset.setName("Pick Up");
        asset.setCode("112233");
        asset.setLoc("Yard");
        asset.setParent(true);
        assetList.add(asset);
        asset = new ToolhawkAsset();
        asset.setName("Step Van");
        asset.setCode("334455");
        asset.setLoc("Annex");
        asset.setParent(false);
        assetList.add(asset);
        asset = new ToolhawkAsset();
        asset.setName("Pick Up");
        asset.setCode("112233");
        asset.setLoc("Yard");
        asset.setParent(true);
        assetList.add(asset);*/

        assetList = DataManager.getInstance().getAllToolhawkEquipmentForLocation(locationCode);
        tvExpected.setText("" + assetList.size());
        mAdapter = new QuickCountAdapter(QuickCountActivity.this, assetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(QuickCountActivity.this);
        rvQuickCount.setLayoutManager(mLayoutManager);
        rvQuickCount.setItemAnimator(new DefaultItemAnimator());
        rvQuickCount.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }




    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(QuickCountActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(QuickCountActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(QuickCountActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuickCountActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(QuickCountActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuickCountActivity.this);
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
                    ActivityCompat.requestPermissions(QuickCountActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(QuickCountActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(QuickCountActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(QuickCountActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuickCountActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(QuickCountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.btnScan: {
                    boolean isfound = false;
                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();
                    } else {
                        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(etBarcode.getText().toString().trim());
                        if (null != toolhawkEquipment) {
                            int assetPos = 0;
                            for (int i = 0; i < assetList.size(); i++) {


                                assetPos = i;
                                if (toolhawkEquipment.getCode().equals(assetList.get(i).getCode())) {
                                    isfound = true;
                                    break;
                                }

                            }
                            if (isfound) {
                                mAdapter.removeAt(assetPos);
                                showToast("Asset Found!");
                                assetsFound = assetsFound + 1;
                                tvFound.setText("" + assetsFound);
                                isfound =false;
                                if (tvExpected.getText().toString().equals(tvFound.getText().toString())) {
                                    showToast("Quick Count Complete!");
                                    sendMessage("finish");
                                    finish();
                                }

                            } else {
                                new AlertDialog.Builder(QuickCountActivity.this)
                                        .setTitle("Quick Count")
                                        .setMessage(toolhawkEquipment.getCode() + " doesn't belong to" + tvLocName.getText().toString() + ", do you want to Continue?")
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                unexpectedAssets = unexpectedAssets + 1;
                                                tvUnExpected.setText("" + unexpectedAssets);
                                                etBarcode.setText("");
                                                hideKeyboard();
                                            }
                                        })
                                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                              etBarcode.setText("");
                                                hideKeyboard();
                                            }
                                        })
                                        .show();

                            }
                        } else {
                            showToast("No equipment found!");
                            hideKeyboard();
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

        boolean isfound = false;
        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(message);

        if (null != toolhawkEquipment) {
            int assetPos = 0;
            for (int i = 0; i < assetList.size(); i++) {


                assetPos = i;
                if (toolhawkEquipment.getCode().equals(assetList.get(i).getCode())) {
                    isfound = true;
                    break;
                }

            }
            if (isfound) {
                mAdapter.removeAt(assetPos);
                showToast("Asset Found!");
                assetsFound = assetsFound + 1;
                tvFound.setText("" + assetsFound);
                isfound =false;

                if (tvExpected.getText().toString().equals(tvFound.getText().toString())) {
                    showToast("Quick Count Complete!");
                    finish();
                }

            } else {
                new AlertDialog.Builder(QuickCountActivity.this)
                        .setTitle("Quick Count")
                        .setMessage(toolhawkEquipment.getCode() + " doesn't belong to " + tvLocName.getText().toString() + ", do you want to Continue?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                unexpectedAssets = unexpectedAssets + 1;
                                tvUnExpected.setText("" + unexpectedAssets);

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

            }
        } else {
            showToast("No equipment found!");
        }

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
}

