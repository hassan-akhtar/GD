package com.ets.gd.FireBug.RouteInspection;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Adapters.RouteAssetAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteAsset;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteLocation;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class RouteAssetActivity extends AppCompatActivity implements BarcodeScan {

    public static RouteLocation routeLocation;
    TextView tbTitleTop, tbTitleBottom, tvCompanyName, tvAssetCount, tvLocCount, tvRouteName, tvLocName, tvBarcodeTitle, tvBarcodeValue, tvUnderText;
    Button btnCross, btnScan;
    EditText etBarcode;
    ImageView ivBack, ivTick;
    String compName, locCount, routeName;
    private List<FireBugEquipment> equipmentList = new ArrayList<FireBugEquipment>();
    RecyclerView rvRouteInspection;
    List<RouteAsset> assetList = new ArrayList<>();
    FireBugEquipment fireBugEquipment;
    static RouteAssetAdapter routeAssetAdapter;
    SharedPreferencesManager sharedPreferencesManager;
    int cusID;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_insp_asset);
        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvAssetCount = (TextView) findViewById(R.id.tvAssetCount);
        tvLocCount = (TextView) findViewById(R.id.tvLocCount);
        tvRouteName = (TextView) findViewById(R.id.tvRouteName);
        tvLocName = (TextView) findViewById(R.id.tvLocName);
        tvCompanyName = (TextView) findViewById(R.id.tvCompanyValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        rvRouteInspection = (RecyclerView) findViewById(R.id.rvRouteInspection);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnScan = (Button) findViewById(R.id.btnScan);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        tvUnderText.setText("Enter/Scan Asset ID");
        tbTitleBottom.setText("Route Inspection");
        compName = getIntent().getStringExtra("compName");
        locCount = getIntent().getStringExtra("locCount");
        routeName = getIntent().getStringExtra("routeName");
        cusID = getIntent().getIntExtra("cusID",0);
        tvCompanyName.setText(compName);
        tvAssetCount.setText("" + routeLocation.getRouteAssets().size());
        tvLocCount.setText("" + locCount);
        tvRouteName.setText("" + routeName);
        tvLocName.setText("" + DataManager.getInstance().getLocationByID(routeLocation.getLocationID()).getCode());
        ivTick.setVisibility(View.GONE);

    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(RouteAssetActivity.this);
        assetList.addAll(routeLocation.getRouteAssets());

        for (RouteAsset routeAsset : assetList) {
            FireBugEquipment eq = DataManager.getInstance().getEquipmentByID(routeAsset.getEquipmentID());
            equipmentList.add(eq);
        }
        routeAssetAdapter = new RouteAssetAdapter(RouteAssetActivity.this, equipmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvRouteInspection.setLayoutManager(mLayoutManager);
        rvRouteInspection.setItemAnimator(new DefaultItemAnimator());
        rvRouteInspection.setAdapter(routeAssetAdapter);
    }

    private void initListeners() {
        // btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);


        rvRouteInspection.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(RouteAssetActivity.this, rvRouteInspection, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FireBugEquipment fireBugEquipment = DataManager.getInstance().getEquipmentByID(assetList.get(position).getEquipmentID());

                if (!fireBugEquipment.isRouteUnitInspected()) {
                    RouteAssetInspectionActivity.routeAsset = null;
                    RouteAssetInspectionActivity.routeAsset = assetList.get(position);
                    Intent in = new Intent(RouteAssetActivity.this, RouteAssetInspectionActivity.class);
                    in.putExtra("compName", tvCompanyName.getText().toString());
                    in.putExtra("tag", "" + equipmentList.get(position).getCode());
                    in.putExtra("loc", tvLocName.getText().toString());
                    in.putExtra("routeName", tvRouteName.getText().toString());
                    in.putExtra("deviceTypeID", assetList.get(position).getDeviceTypeID());
                    in.putExtra("deviceType", equipmentList.get(position).getModel().getCode());
                    in.putExtra("cusID", cusID);
                    in.putExtra("equipmentID", equipmentList.get(position).getID());
                    in.putExtra("desp", equipmentList.get(position).getManufacturer().getCode());
                    in.putExtra("RouteID", assetList.get(position).getRouteID());
                    in.putExtra("AssetCount", tvAssetCount.getText().toString());
                    in.putExtra("LocCount", tvLocCount.getText().toString());
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), "This Asset is Already Inspected", Toast.LENGTH_LONG).show();
                }

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
                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();


                    } else {
                        fireBugEquipment = DataManager.getInstance().getEquipment(etBarcode.getText().toString().trim());
                        if (null != fireBugEquipment) {

                            for (FireBugEquipment eq : equipmentList) {
                                if (eq.getID() == fireBugEquipment.getID()) {

                                    break;
                                }
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Asset not found in " + "Route Location " + DataManager.getInstance().getLocationByID(routeLocation.getLocationID()).getCode() + "", Toast.LENGTH_LONG).show();
                        }

                    }
                    break;
                }

                case R.id.ivBack: {
                    finish();
                }
                break;
            }
        }

    };

    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(RouteAssetActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", "Route Inspection");
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(RouteAssetActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(RouteAssetActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(RouteAssetActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(RouteAssetActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RouteAssetActivity.this);
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
                    ActivityCompat.requestPermissions(RouteAssetActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(RouteAssetActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", "Route Inspection");
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
                Intent in = new Intent(RouteAssetActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", "Route Inspection");
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(RouteAssetActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(RouteAssetActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(RouteAssetActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

    @Override
    public void BarcodeScanned(Barcode barcode) {

    }
}
