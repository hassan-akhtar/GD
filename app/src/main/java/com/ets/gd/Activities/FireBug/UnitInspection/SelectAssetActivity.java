package com.ets.gd.Activities.FireBug.UnitInspection;

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
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.FireBug.Move.SelectLocationActivity;
import com.ets.gd.Activities.Scan.BarcodeScanActivity;
import com.ets.gd.Activities.Scan.CommonFirebugScanActivity;
import com.ets.gd.Adapters.ScannedAssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.AssetReplaced;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.Barcode;
import com.ets.gd.Models.Location;
import com.ets.gd.Models.Replace;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class SelectAssetActivity extends AppCompatActivity implements BarcodeScan {


    ImageView ivBack, ivChangeCompany, ivTick;
    EditText etBarcode;
    ScannedAssetsAdapter mAdapter;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvUnderText, tvBarcodeTitle, tvBarcodeValue;
    Button btnScan, btnCross;
    RecyclerView rlLocs;
    public static List<FireBugEquipment> assetList = new ArrayList<FireBugEquipment>();
    Context mContext;
    String compName, repairSelection;
    SharedPreferencesManager sharedPreferencesManager;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_asset);

        initViews();
        initObj();
        initListeners();

    }


    private void initViews() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        btnScan = (Button) findViewById(R.id.btnScan);
        rlLocs = (RecyclerView) findViewById(R.id.rlLocs);
        btnCross = (Button) findViewById(R.id.btnCross);
        ivChangeCompany.setVisibility(View.GONE);
        ivTick.setVisibility(View.GONE);
        tbTitleTop.setText("Firebug");
        tbTitleBottom.setText("Select Asset");
        tvUnderText.setText("Enter Asset ID");
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(SelectAssetActivity.this);
        BarcodeScanActivity.barcodeScan = this;
        mContext = this;
        compName = getIntent().getStringExtra("compName");
        repairSelection = getIntent().getStringExtra("repairSelection");
        tvCompanyValue.setText(compName);
        hideKeyboard();
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        //setupLocList();
        mAdapter = new ScannedAssetsAdapter(getApplicationContext(), assetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rlLocs.setLayoutManager(mLayoutManager);
        rlLocs.setItemAnimator(new DefaultItemAnimator());
        rlLocs.setAdapter(mAdapter);
    }


    public void hideKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);

        rlLocs.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(SelectAssetActivity.this, rlLocs, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Intent in = new Intent(SelectAssetActivity.this,RepairAssetActivity.class);
//                in.putExtra("compName",tvCompanyValue.getText().toString().trim());
//                in.putExtra("tagID",assetList.get(position).getCode());
//                startActivity(in);
                sendMessage(assetList.get(position).getCode() + " " + repairSelection);
                ReplaceAssetActivity.newLocID = assetList.get(position).getLocation().getID();
                ReplaceAssetActivity.newEquipID = assetList.get(position).getID();
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.btnScan: {
                    setBarcodeScannedType();
                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();
                    } else {
                        FireBugEquipment fireBugEquipment = DataManager.getInstance().getEquipment(etBarcode.getText().toString().toString());
                        if (null != fireBugEquipment) {
//                            Intent in = new Intent(SelectAssetActivity.this,RepairAssetActivity.class);
//                            in.putExtra("compName",tvCompanyValue.getText().toString().trim());
//                            in.putExtra("tagID",fireBugEquipment.getCode());
//                            startActivity(in);
                            sendMessage(fireBugEquipment.getCode() + " " + repairSelection);
                            ReplaceAssetActivity.newLocID = fireBugEquipment.getLocation().getID();
                            ReplaceAssetActivity.newEquipID = fireBugEquipment.getID();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Asset not found", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                }
            }
        }

    };

    private void setBarcodeScannedType() {
        BarcodeScanActivity.barcodeScan = this;
    }


    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("moveToLoc");
        intent.putExtra("message", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void BarcodeScanned(Barcode barcode) {
        String message = barcode.getMessage();
        String task = barcode.getTask();
        if (task.startsWith("loc")) {

            FireBugEquipment fireBugEquipment = null;

            for (FireBugEquipment asset : assetList) {

                if (message.toLowerCase().equals(asset.getCode().toLowerCase())) {
                    fireBugEquipment = asset;
                    break;
                }
            }

            if (null != fireBugEquipment) {
                if (null!=fireBugEquipment.getLocation()) {
                    ReplaceAssetActivity.newLocID = fireBugEquipment.getLocation().getID();
                }
                ReplaceAssetActivity.newEquipID = fireBugEquipment.getID();
                sendMessage(fireBugEquipment.getCode() + " " + repairSelection);
                finish();
//                    Intent in = new Intent(SelectAssetActivity.this,RepairAssetActivity.class);
//                    in.putExtra("compName",tvCompanyValue.getText().toString().trim());
//                    in.putExtra("tagID",fireBugEquipment.getCode());
//                    startActivity(in);

            } else {
                Toast.makeText(getApplicationContext(), "Asset not found", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(SelectAssetActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", "loc");
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(SelectAssetActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SelectAssetActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectAssetActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(SelectAssetActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectAssetActivity.this);
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
                    ActivityCompat.requestPermissions(SelectAssetActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(SelectAssetActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", "loc");
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
                Intent in = new Intent(SelectAssetActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", "loc");
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SelectAssetActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectAssetActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(SelectAssetActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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
}
