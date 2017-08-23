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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Adapters.RouteInspLocAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.FireBug.Scan.CommonFirebugScanActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Interfaces.RouteCompleted;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.RequestDTOs.UnitinspectionResult;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteAsset;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.Routes;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;
import com.squareup.okhttp.Route;

import java.util.ArrayList;
import java.util.List;

public class RouteLocationActivity extends AppCompatActivity implements BarcodeScan {


    TextView tbTitleTop, tbTitleBottom, tvCompanyName, tvRouteType, tvDesc, tvRouteName, tvLocCount;
    ImageView ivBack, ivTick;
    String compName, taskType;
    Button btnSearchLoc;
    public static Routes route;
    RecyclerView rvRouteInspection;
    int cusID;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    RouteInspLocAdapter routeInspLocAdapter;
    List<RouteLocation> locationList = new ArrayList<>();
    SharedPreferencesManager sharedPreferencesManager;
    public static RouteCompleted routeCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_insp_location);
        initViews();
        initObj();
        initListeners();
    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvRouteType = (TextView) findViewById(R.id.tvRouteType);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvRouteName = (TextView) findViewById(R.id.tvRouteName);
        tvLocCount = (TextView) findViewById(R.id.tvLocCount);
        tvCompanyName = (TextView) findViewById(R.id.tvCompanyValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        btnSearchLoc = (Button) findViewById(R.id.btnSearchLoc);
        rvRouteInspection = (RecyclerView) findViewById(R.id.rvRouteInspection);
        tbTitleBottom.setText("Route Inspection");
        compName = getIntent().getStringExtra("compName");
        cusID = getIntent().getIntExtra("cusID", 0);

        for (int i=0;i<route.getRouteLocations().size() ;i++){
            if (null!=route.getRouteLocations().get(i).getRouteAssets()) {
                if(0!=route.getRouteLocations().get(i).getRouteAssets().size()){
                    locationList.add(route.getRouteLocations().get(i));
                }
            }
        }

        tvRouteName.setText(route.getCode());
        tvDesc.setText(route.getDescription());
        tvRouteType.setText(route.getRouteType());
        taskType = "Route Inspection";
        tvLocCount.setText("" + locationList.size());
        routeInspLocAdapter = new RouteInspLocAdapter(RouteLocationActivity.this, locationList);
        tvCompanyName.setText(compName);
        // ivTick.setVisibility(View.GONE);
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(RouteLocationActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RouteLocationActivity.this);
        rvRouteInspection.setLayoutManager(mLayoutManager);
        rvRouteInspection.setItemAnimator(new DefaultItemAnimator());
        rvRouteInspection.setAdapter(routeInspLocAdapter);
        routeInspLocAdapter.notifyDataSetChanged();
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
        btnSearchLoc.setOnClickListener(mGlobal_OnClickListener);
        rvRouteInspection.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(RouteLocationActivity.this, rvRouteInspection, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (!tvRouteType.getText().toString().equals("Structured")) {
                    if (0 != locationList.get(position).getRouteAssets().size()) {
                        RouteAssetActivity.routeLocation = null;
                        RouteAssetActivity.routeLocation = locationList.get(position);
                        Intent in = new Intent(RouteLocationActivity.this, RouteAssetActivity.class);
                        in.putExtra("compName", tvCompanyName.getText().toString());
                        in.putExtra("locCount", "" + locationList.size());
                        in.putExtra("cusID", cusID);
                        in.putExtra("routeName", tvRouteName.getText().toString());
                        startActivity(in);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Route Location Assets(s) Found.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    if (checkRouteTypeValidation(position)) {
                        if (0 != locationList.get(position).getRouteAssets().size()) {
                            RouteAssetActivity.routeLocation = null;
                            RouteAssetActivity.routeLocation = locationList.get(position);
                            Intent in = new Intent(RouteLocationActivity.this, RouteAssetActivity.class);
                            in.putExtra("compName", tvCompanyName.getText().toString());
                            in.putExtra("locCount", "" + locationList.size());
                            in.putExtra("cusID", cusID);
                            in.putExtra("routeName", tvRouteName.getText().toString());
                            startActivity(in);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Route Location Assets(s) Found.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Inspect previous Location(s) first!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private boolean checkRouteTypeValidation(int pos) {

        if (0 != pos) {
            boolean areAllAssetsInspected = true;
            List<RouteAsset> assetList = new ArrayList<>();
            List<UnitinspectionResult> unitinspectionResults = new ArrayList<>();
            assetList = locationList.get(pos - 1).getRouteAssets();
            unitinspectionResults = DataManager.getInstance().getUnitinspectionResults();
            if (0 != unitinspectionResults.size() && 0 != assetList.size()) {
                List<Integer> routeAssetIDs = new ArrayList<>();
                for (int i = 0; i < unitinspectionResults.size(); i++) {
                    routeAssetIDs.add(unitinspectionResults.get(i).getRouteAssetID());
                }
                for (int j = 0; j < assetList.size(); j++) {
                    if (!routeAssetIDs.contains(assetList.get(j).getID())) {
                        areAllAssetsInspected = false;
                        break;
                    }
                }


            } else if (0 == unitinspectionResults.size() && 0 != assetList.size()) {
                areAllAssetsInspected = false;
            }


            if (areAllAssetsInspected) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }


    }


    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivTick: {

                    if (null != DataManager.getInstance().getRoute(tvRouteName.getText().toString())) {
                        DataManager.getInstance().markRouteComplete(tvRouteName.getText().toString());
                        routeCompleted.routeCompleted(tvRouteName.getText().toString());
                        finish();
                    }
                    break;
                }


                case R.id.btnSearchLoc: {
                    checkCameraPermission();
                    break;
                }

            }
        }

    };


    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(RouteLocationActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(RouteLocationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(RouteLocationActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(RouteLocationActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(RouteLocationActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RouteLocationActivity.this);
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
                    ActivityCompat.requestPermissions(RouteLocationActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(RouteLocationActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(RouteLocationActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(RouteLocationActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(RouteLocationActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(RouteLocationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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
        String message = barcode.getMessage();
        Locations locScanned = DataManager.getInstance().getLocation(message);
        boolean isFound = false;
        int pos = 0;
        if (null != locScanned) {
            for (int i = 0; i < locationList.size(); i++) {
                Locations loc = DataManager.getInstance().getLocationByID(locationList.get(i).getLocationID());


                if (loc.getCode().toLowerCase().equals(locScanned.getCode().toLowerCase())) {
                    isFound = true;
                    pos = i;
                    break;
                }
            }

            if (isFound) {

                if (!tvRouteType.getText().toString().equals("Structured")) {
                    if (0 != locationList.get(pos).getRouteAssets().size()) {
                        RouteAssetActivity.routeLocation = null;
                        RouteAssetActivity.routeLocation = locationList.get(pos);
                        Intent in = new Intent(RouteLocationActivity.this, RouteAssetActivity.class);
                        in.putExtra("compName", tvCompanyName.getText().toString());
                        in.putExtra("locCount", "" + locationList.size());
                        in.putExtra("cusID", cusID);
                        in.putExtra("routeName", tvRouteName.getText().toString());
                        startActivity(in);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Route Location Assets(s) Found.", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if (checkRouteTypeValidation(pos)) {
                        if (0 != locationList.get(pos).getRouteAssets().size()) {
                            RouteAssetActivity.routeLocation = null;
                            RouteAssetActivity.routeLocation = locationList.get(pos);
                            Intent in = new Intent(RouteLocationActivity.this, RouteAssetActivity.class);
                            in.putExtra("compName", tvCompanyName.getText().toString());
                            in.putExtra("locCount", "" + locationList.size());
                            in.putExtra("cusID", cusID);
                            in.putExtra("routeName", tvRouteName.getText().toString());
                            startActivity(in);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Route Location Assets(s) Found.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Inspect previous Location(s) first!", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                showToast("Location not Found In this Route!");
            }
        } else {
            showToast("No Location Found!");
        }
    }

    void showToast(String msg) {
        Toast.makeText(RouteLocationActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}
