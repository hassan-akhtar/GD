package com.ets.gd.Activities.Scan;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.FireBug.Move.LocationSelectionActivity;
import com.ets.gd.Activities.FireBug.UnitInspection.UnitInspectionActivity;
import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Activities.FireBug.ViewInformation.ViewLocationInformationActivity;
import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Adapters.ScannedAssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.CustomerFragment;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.AssetList;
import com.ets.gd.Models.InspectionDates;
import com.ets.gd.Models.Location;
import com.ets.gd.Models.NewAsset;
import com.ets.gd.Models.Note;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class CommonFirebugScanActivity extends AppCompatActivity {


    SharedPreferencesManager sharedPreferencesManager;
    ImageView ivBack, ivChangeCompany, ivTick;
    EditText etBarcode;
    ScannedAssetsAdapter mAdapter;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvUnderText, tvBarcodeTitle, tvBarcodeValue, tvInspectAsset, tvChangeLocation;
    TextView tvCount, tvCountSupportText, tvTaskName;
    RelativeLayout rlForwardArrow, rlBottomSheet, rlBottomSheetUnitInsp;
    Button btnLoc, btnAsset, btnScan, btnCross;
    View vLine;
    RecyclerView rlAssets;
    Asset asset;
    String taskType, compName;
    LinearLayout llbtns, llunderText;
    private List<Asset> assetList = new ArrayList<Asset>();
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_firebug);
        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvInspectAsset = (TextView) findViewById(R.id.tvInspectAsset);
        tvChangeLocation = (TextView) findViewById(R.id.tvChangeLocation);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCountSupportText = (TextView) findViewById(R.id.tvCountSupportText);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        rlForwardArrow = (RelativeLayout) findViewById(R.id.rlForwardArrow);
        rlBottomSheet = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);
        rlBottomSheetUnitInsp = (RelativeLayout) findViewById(R.id.rlBottomSheetUnitInsp);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        vLine = (View) findViewById(R.id.vLine);
        btnLoc = (Button) findViewById(R.id.btnLoc);
        btnAsset = (Button) findViewById(R.id.btnAsset);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtns);
        rlAssets = (RecyclerView) findViewById(R.id.lvAssets);
        llunderText = (LinearLayout) findViewById(R.id.llunderText);
        ivChangeCompany.setVisibility(View.GONE);
        ivTick.setVisibility(View.GONE);
    }

    private void initObj() {
        mContext = this;
        hideKeyboard();
        assetList.clear();
        sharedPreferencesManager = new SharedPreferencesManager(CommonFirebugScanActivity.this);
        taskType = getIntent().getStringExtra("taskType");
        compName = getIntent().getStringExtra("compName");
        tbTitleBottom.setText(taskType);
        tvCompanyValue.setText(compName);
        if (taskType.toLowerCase().startsWith("v")) {
            tvUnderText.setText("Enter ID of Asset/Location or tap Scan");
        } else {
            tvUnderText.setText("Enter Asset ID or tap Scan");
        }
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBarcodeBroadcastReceiver,
                new IntentFilter("barcode-scanned"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
        mAdapter = new ScannedAssetsAdapter(getApplicationContext(), assetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rlAssets.setLayoutManager(mLayoutManager);
        rlAssets.setItemAnimator(new DefaultItemAnimator());
        rlAssets.setAdapter(mAdapter);
    }

    private void initListeners() {
        btnLoc.setOnClickListener(mGlobal_OnClickListener);
        btnAsset.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);
        ivChangeCompany.setOnClickListener(mGlobal_OnClickListener);
        tvChangeLocation.setOnClickListener(mGlobal_OnClickListener);
        tvInspectAsset.setOnClickListener(mGlobal_OnClickListener);

        rlAssets.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getApplicationContext(), rlAssets, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);
                alertDialogBuilder.setTitle("Remove Asset");
                alertDialogBuilder.setMessage("Are you sure you want to remove " + assetList.get(position).getManufacturers() + ", " + assetList.get(position).getModel());
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("REMOVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        mAdapter.removeAt(position);
                                        if (taskType.toLowerCase().startsWith("m") || taskType.toLowerCase().startsWith("v")) {
                                            if (0 != assetList.size()) {
                                                tvCount.setText("" + assetList.size());
                                            } else {
                                                rlBottomSheet.setVisibility(View.GONE);
                                            }
                                        } else if (taskType.toLowerCase().startsWith("in")) {
                                            if (0 == assetList.size()) {
                                                rlBottomSheetUnitInsp.setVisibility(View.GONE);
                                            }
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

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {
                    if (taskType.toLowerCase().startsWith("v")) {
                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {
                            tvBarcodeValue.setText(etBarcode.getText().toString().trim());
                            etBarcode.setText("");
                            etBarcode.setVisibility(View.INVISIBLE);
                            tvBarcodeTitle.setVisibility(View.VISIBLE);
                            tvBarcodeValue.setVisibility(View.VISIBLE);
                            btnCross.setVisibility(View.VISIBLE);
                            llunderText.setVisibility(View.GONE);
                            llbtns.setVisibility(View.VISIBLE);
                        }
                    } else if (taskType.toLowerCase().startsWith("m")) {
                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {
                            asset = DataManager.getInstance().getAsset(etBarcode.getText().toString().trim());
                            if (null != asset) {
                                if (!assetList.contains(asset)) {
                                    hideKeyboard();
                                    etBarcode.setText("");
                                    rlBottomSheet.setVisibility(View.VISIBLE);
                                    assetList.add(asset);
                                    tvTaskName.setText("MOVE ASSET");
                                    tvCount.setText("" + assetList.size());
                                    tvCountSupportText.setText("Asset Selected to Move");
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    hideKeyboard();
                                    Toast.makeText(getApplicationContext(), "Asset Already Added!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                hideKeyboard();
                                Toast.makeText(getApplicationContext(), "Asset Not found!", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else if (taskType.toLowerCase().startsWith("t")) {
                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {
                            asset = DataManager.getInstance().getAsset(etBarcode.getText().toString().trim());
                            if (null != asset) {
                                if (!assetList.contains(asset)) {
                                    hideKeyboard();
                                    etBarcode.setText("");
                                    rlBottomSheet.setVisibility(View.VISIBLE);
                                    assetList.add(asset);
                                    tvTaskName.setText("TRANSFER ASSET");
                                    tvCount.setText("" + assetList.size());
                                    tvCountSupportText.setText("Asset Selected to TRANSFER");
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    hideKeyboard();
                                    Toast.makeText(getApplicationContext(), "Asset Already Added!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                hideKeyboard();
                                Toast.makeText(getApplicationContext(), "Asset Not found!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else if (taskType.toLowerCase().startsWith("ins")) {
                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {
                            asset = DataManager.getInstance().getAsset(etBarcode.getText().toString().trim());
                            if (null != asset) {
                                if (!assetList.contains(asset)) {
                                    assetList.clear();
                                    etBarcode.setText("");
                                    hideKeyboard();
                                    rlBottomSheetUnitInsp.setVisibility(View.VISIBLE);
                                    assetList.add(asset);
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    hideKeyboard();
                                    Toast.makeText(getApplicationContext(), "Asset Already Added!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                hideKeyboard();
                                Toast.makeText(getApplicationContext(), "Asset Not found!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    break;
                }

                case R.id.btnAsset: {

                    asset = DataManager.getInstance().getAsset(tvBarcodeValue.getText().toString().trim());
                    ViewAssetInformationActivity.barCodeID = tvBarcodeValue.getText().toString().trim();
                    if (null != asset) {
                        Intent in = new Intent(CommonFirebugScanActivity.this, ViewAssetInformationActivity.class);
                        in.putExtra("action", "viewAsset");
                        startActivity(in);
                        hideScannedData();
                    } else {
                        Toast.makeText(getApplicationContext(), "Asset Not found!", Toast.LENGTH_LONG).show();
                    }

                    break;
                }

                case R.id.btnLoc: {
                    ViewLocationInformationActivity.barCodeID = tvBarcodeValue.getText().toString().trim();
                    if (null != DataManager.getInstance().getLocation(tvBarcodeValue.getText().toString().trim())) {
                        Intent in = new Intent(CommonFirebugScanActivity.this, ViewLocationInformationActivity.class);
                        in.putExtra("action", "viewLoc");
                        startActivity(in);
                        hideScannedData();
                    } else {
                        Toast.makeText(getApplicationContext(), "Location Not found!", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

                case R.id.ivBack: {
                    finish();
                    break;
                }


                case R.id.tvInspectAsset: {
                    Intent in = new Intent(CommonFirebugScanActivity.this, UnitInspectionActivity.class);
                    in.putExtra("tag",""+assetList.get(0).getTagID());
                    in.putExtra("loc",""+assetList.get(0).getLocation().getLocationID());
                    in.putExtra("compName",compName);
                    in.putExtra("deviceType",""+assetList.get(0).getDeviceType());
                    in.putExtra("desp",""+assetList.get(0).getLocation().getDescription());
                    startActivity(in);
                    break;
                }


                case R.id.tvChangeLocation: {
                    showToast("Change Loc");
                    break;
                }

                case R.id.ivChangeCompany: {
                    finish();
                    break;
                }

                case R.id.btnCross: {
                    hideScannedData();
                    break;
                }

                case R.id.rlForwardArrow: {

                    AssetList listAssets = new AssetList();
                    listAssets.setAssetList(assetList);
                    Intent in = new Intent(CommonFirebugScanActivity.this, LocationSelectionActivity.class);
                    in.putExtra("taskType", taskType);
                    in.putExtra("compName", compName);
                    in.putExtra("count", assetList.size());
                    if (null != assetList.get(0).getLocation()) {
                        in.putExtra("loc", assetList.get(0).getLocation().getLocationID());
                    } else {
                        in.putExtra("loc", "L007133");
                    }
                    LocationSelectionActivity.asset = assetList;
                    startActivity(in);
                    break;
                }
            }
        }

    };

    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            Intent in = new Intent(CommonFirebugScanActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(CommonFirebugScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(CommonFirebugScanActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommonFirebugScanActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(CommonFirebugScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommonFirebugScanActivity.this);
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
                    ActivityCompat.requestPermissions(CommonFirebugScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                Intent in = new Intent(CommonFirebugScanActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(CommonFirebugScanActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(CommonFirebugScanActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommonFirebugScanActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(CommonFirebugScanActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                etBarcode.getWindowToken(), 0);
    }

    private final BroadcastReceiver mBarcodeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            String task = intent.getStringExtra("taskType");
            if (!task.startsWith("loc")) {
                if (taskType.toLowerCase().startsWith("v")) {
                    tvBarcodeValue.setText(message);
                    etBarcode.setVisibility(View.INVISIBLE);
                    tvBarcodeTitle.setVisibility(View.VISIBLE);
                    tvBarcodeValue.setVisibility(View.VISIBLE);
                    btnCross.setVisibility(View.VISIBLE);
                    llunderText.setVisibility(View.GONE);
                    llbtns.setVisibility(View.VISIBLE);
                } else if (taskType.toLowerCase().startsWith("m")) {
                    asset = DataManager.getInstance().getAsset(message);
                    if (null != asset) {
                        if (!assetList.contains(asset)) {
                            etBarcode.setText("");
                            rlBottomSheet.setVisibility(View.VISIBLE);
                            assetList.add(asset);
                            tvTaskName.setText("MOVE ASSET");
                            tvCount.setText("" + assetList.size());
                            tvCountSupportText.setText("Asset Selected to Move");
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Asset Already Added!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Asset Not found!", Toast.LENGTH_LONG).show();
                    }


                } else if (taskType.toLowerCase().startsWith("t")) {


                    asset = DataManager.getInstance().getAsset(message);

                    if (null != asset) {
                        if (!assetList.contains(asset)) {
                            etBarcode.setText("");
                            rlBottomSheet.setVisibility(View.VISIBLE);
                            assetList.add(asset);
                            tvTaskName.setText("TRANSFER ASSET");
                            tvCount.setText("" + assetList.size());
                            tvCountSupportText.setText("Asset Selected to TRANSFER");
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Asset Already Added!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Asset Not found!", Toast.LENGTH_LONG).show();
                    }

                } else if (taskType.toLowerCase().startsWith("ins")) {

                    asset = DataManager.getInstance().getAsset(message);

                    if (null != asset) {
                        if (!assetList.contains(asset)) {
                            assetList.clear();
                            etBarcode.setText("");
                            rlBottomSheetUnitInsp.setVisibility(View.VISIBLE);
                            assetList.add(asset);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Asset Already Added!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Asset Not found!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    showToast(taskType + ": " + message);
                }
            }


        }
    };


    private final BroadcastReceiver mMoveCompleteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");

            if (message.startsWith("fin")) {
                finish();
            }

        }
    };

    void hideScannedData() {
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        llunderText.setVisibility(View.VISIBLE);
        etBarcode.setVisibility(View.VISIBLE);
        llbtns.setVisibility(View.GONE);
    }

    void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
