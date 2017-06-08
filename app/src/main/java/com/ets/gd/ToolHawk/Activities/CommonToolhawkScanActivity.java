package com.ets.gd.ToolHawk.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.EquipmentInfo.EquipmentInfoActivity;
import com.ets.gd.ToolHawk.EquipmentInfo.ToolhawkLocationActivity;
import com.ets.gd.ToolHawk.Maintenance.MaintenanceActivity;
import com.ets.gd.ToolHawk.QuickCount.QuickCountActivity;
import com.ets.gd.ToolHawk.Transfer.TransferActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.List;

public class CommonToolhawkScanActivity extends AppCompatActivity implements BarcodeScan {


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText;
    Button btnCross, btnNewCount, btnExistingCount, btnScan, btnLocation, btnAsset;
    LinearLayout llbtns, llbtnsEq;
    EditText etBarcode;
    ImageView ivInfo;
    String taskType;
    ImageView ivBack, ivTick;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    SharedPreferencesManager sharedPreferencesManager;
    ToolhawkEquipment toolhawkEquipment;
    ETSLocation etsLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_toolhawk_scan);

        initViews();
        initObj();
        initListeners();
        setupView();

    }


    private void initViews() {

        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnNewCount = (Button) findViewById(R.id.btnNewCount);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        btnAsset = (Button) findViewById(R.id.btnAsset);
        btnExistingCount = (Button) findViewById(R.id.btnExistingCount);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtnsQuickCount);
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
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);

        if (tbTitleBottom.getText().toString().toLowerCase().startsWith("qu")) {
            tvUnderText.setText("Scan/Enter ID of Location");
        }

        if (tbTitleBottom.getText().toString().toLowerCase().startsWith("eq")) {
            tvUnderText.setText("Scan/Enter ID of Asset/Location");
        }
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(CommonToolhawkScanActivity.this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnNewCount.setOnClickListener(mGlobal_OnClickListener);
        btnExistingCount.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
        btnLocation.setOnClickListener(mGlobal_OnClickListener);
        btnAsset.setOnClickListener(mGlobal_OnClickListener);
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
    }


    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnCross: {
                    hideEnteredData();
                    break;
                }

                case R.id.btnLocation: {
                    etsLocation = DataManager.getInstance().getETSLocationByCode(tvBarcodeValue.getText().toString());
                    if (null != etsLocation) {
                        Intent in = new Intent(CommonToolhawkScanActivity.this, ToolhawkLocationActivity.class);
                        in.putExtra("taskType", "view");
                        in.putExtra("barcodeID", tvBarcodeValue.getText().toString());
                        startActivity(in);
                        etBarcode.setText("");
                        hideEnteredData();
                    } else {
                        showToast("No Location Found!");
                    }
                    break;
                }


                case R.id.btnAsset: {
                    toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(tvBarcodeValue.getText().toString());
                    if (null != toolhawkEquipment) {
                        Intent in = new Intent(CommonToolhawkScanActivity.this, EquipmentInfoActivity.class);
                        in.putExtra("taskType", "view");
                        in.putExtra("barcodeID", tvBarcodeValue.getText().toString());
                        startActivity(in);
                        etBarcode.setText("");
                        hideEnteredData();
                    } else {
                        showToast("No Equipment Found!");
                    }
                    break;
                }


                case R.id.btnNewCount: {
                    ETSLocations etsLocation = DataManager.getInstance().getETSLocationsByCode(tvBarcodeValue.getText().toString());

                    if (null != etsLocation) {
                        List<ToolhawkEquipment> assetList = DataManager.getInstance().getAllToolhawkEquipmentForLocation(etsLocation.getCode());
                        if (null != assetList && 0 != assetList.size()) {
                            Intent in = new Intent(CommonToolhawkScanActivity.this, QuickCountActivity.class);
                            in.putExtra("taskType", "new");
                            in.putExtra("locationCode", tvBarcodeValue.getText().toString());
                            startActivity(in);
                            tvBarcodeTitle.setVisibility(View.GONE);
                            tvBarcodeValue.setVisibility(View.GONE);
                            ivInfo.setVisibility(View.VISIBLE);
                            tvUnderText.setVisibility(View.VISIBLE);
                            llbtns.setVisibility(View.GONE);
                            tvBarcodeValue.setText("");
                            etBarcode.setVisibility(View.VISIBLE);
                            etBarcode.setText("");
                            btnCross.setVisibility(View.GONE);
                        } else {
                            showToast("No Equipment(s) Found in " + etsLocation.getCode());
                        }
                    } else {
                        showToast("No ETS Location Found!");
                    }
                    break;
                }
                case R.id.btnExistingCount: {
                    ETSLocations etsLocation = DataManager.getInstance().getETSLocationsByCode(tvBarcodeValue.getText().toString());
                    if (null != etsLocation) {
                        List<ToolhawkEquipment> assetList = DataManager.getInstance().getAllToolhawkEquipmentForLocation(etsLocation.getCode());
                        if (null != assetList && 0 != assetList.size()) {
                            Intent in = new Intent(CommonToolhawkScanActivity.this, QuickCountActivity.class);
                            in.putExtra("taskType", "existing");
                            in.putExtra("locationCode", tvBarcodeValue.getText().toString());
                            startActivity(in);
                            tvBarcodeTitle.setVisibility(View.GONE);
                            tvBarcodeValue.setVisibility(View.GONE);
                            ivInfo.setVisibility(View.VISIBLE);
                            tvUnderText.setVisibility(View.VISIBLE);
                            llbtns.setVisibility(View.GONE);
                            tvBarcodeValue.setText("");
                            etBarcode.setVisibility(View.VISIBLE);
                            etBarcode.setText("");
                            btnCross.setVisibility(View.GONE);
                        } else {
                            showToast("No Equipment(s) Found in " + etsLocation.getCode());
                        }
                    } else {
                        showToast("No ETS Location Found!");
                    }
                    break;
                }
                case R.id.btnScan: {
                    if (tbTitleBottom.getText().toString().toLowerCase().startsWith("eq") ) {

                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {
                            showViewForEquipment(etBarcode.getText().toString());
                        }

                    } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("qu")) {
                        if ("".equals(etBarcode.getText().toString().trim())) {
                           checkCameraPermission();
                        } else {
                            showViewForQuickCount();
                        }

                    } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("tra")) {

                        if ("".equals(etBarcode.getText().toString().trim())) {
                            checkCameraPermission();
                        } else {
                            toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(etBarcode.getText().toString());
                            if (null != toolhawkEquipment) {
                                Intent in = new Intent(CommonToolhawkScanActivity.this, TransferActivity.class);
                                in.putExtra("taskName", tbTitleBottom.getText().toString());
                                in.putExtra("eqName", etBarcode.getText().toString());
                                startActivity(in);
                                etBarcode.setText("");
                            } else {
                                showToast("No Equipment Found!");
                            }
                        }


                    } else {
                        Intent in = new Intent(CommonToolhawkScanActivity.this, MaintenanceActivity.class);
                        in.putExtra("assetID", "200020");
                        startActivity(in);
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

    private void showViewForQuickCount() {
        llbtns.setVisibility(View.VISIBLE);
        tvBarcodeTitle.setVisibility(View.VISIBLE);
        tvBarcodeValue.setVisibility(View.VISIBLE);
        tvBarcodeValue.setText(etBarcode.getText().toString());
        etBarcode.setVisibility(View.GONE);
        ivInfo.setVisibility(View.GONE);
        tvUnderText.setVisibility(View.GONE);
        etBarcode.setText("");
        btnCross.setVisibility(View.VISIBLE);
    }

    void hideEnteredData(){
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        ivInfo.setVisibility(View.VISIBLE);
        tvUnderText.setVisibility(View.VISIBLE);
        llbtns.setVisibility(View.GONE);
        llbtnsEq.setVisibility(View.GONE);
        tvBarcodeValue.setText("");
        etBarcode.setVisibility(View.VISIBLE);
        etBarcode.setText("");
        btnCross.setVisibility(View.GONE);
    }

    private void showViewForEquipment(String msg) {
        llbtnsEq.setVisibility(View.VISIBLE);
        tvBarcodeTitle.setVisibility(View.VISIBLE);
        tvBarcodeValue.setVisibility(View.VISIBLE);
        tvBarcodeValue.setText(msg);
        etBarcode.setVisibility(View.GONE);
        ivInfo.setVisibility(View.GONE);
        tvUnderText.setVisibility(View.GONE);
        etBarcode.setText("");
        btnCross.setVisibility(View.VISIBLE);
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
            Intent in = new Intent(CommonToolhawkScanActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(CommonToolhawkScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(CommonToolhawkScanActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommonToolhawkScanActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(CommonToolhawkScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommonToolhawkScanActivity.this);
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
                    ActivityCompat.requestPermissions(CommonToolhawkScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(CommonToolhawkScanActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(CommonToolhawkScanActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(CommonToolhawkScanActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommonToolhawkScanActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(CommonToolhawkScanActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

        if (tbTitleBottom.getText().toString().toLowerCase().startsWith("eq")) {
            showViewForEquipment(message);
        } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("tra")) {
            if (null != toolhawkEquipment) {
                Intent in = new Intent(CommonToolhawkScanActivity.this, TransferActivity.class);
                in.putExtra("taskName", tbTitleBottom.getText().toString());
                in.putExtra("eqName", message);
                startActivity(in);
                etBarcode.setText("");
            } else {
                showToast("No Equipment Found!");
            }

        } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("qui")) {
            ETSLocations etsLocation = DataManager.getInstance().getETSLocationsByCode(message);

            if (null != etsLocation) {
                List<ToolhawkEquipment> assetList = DataManager.getInstance().getAllToolhawkEquipmentForLocation(etsLocation.getCode());
                if (null != assetList && 0 != assetList.size()) {
                    Intent in = new Intent(CommonToolhawkScanActivity.this, QuickCountActivity.class);
                    in.putExtra("taskType", "new");
                    in.putExtra("locationCode", message);
                    startActivity(in);
                    tvBarcodeTitle.setVisibility(View.GONE);
                    tvBarcodeValue.setVisibility(View.GONE);
                    ivInfo.setVisibility(View.VISIBLE);
                    tvUnderText.setVisibility(View.VISIBLE);
                    llbtns.setVisibility(View.GONE);
                    tvBarcodeValue.setText("");
                    etBarcode.setVisibility(View.VISIBLE);
                    etBarcode.setText("");
                    btnCross.setVisibility(View.GONE);
                } else {
                    showToast("No Equipment(s) Found in " + etsLocation.getCode());
                }
            } else {
                showToast("No ETS Location Found!");
            }

        }
    }
}
