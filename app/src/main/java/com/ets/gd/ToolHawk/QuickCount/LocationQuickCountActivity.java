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
import com.ets.gd.Interfaces.EquipmentQuickCountCompleted;
import com.ets.gd.Models.Barcode;
import com.ets.gd.Models.Location;
import com.ets.gd.NetworkLayer.RequestDTOs.QuickCount;
import com.ets.gd.NetworkLayer.RequestDTOs.QuickCountAsset;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Adapters.QuickCountAdapter;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;

public class LocationQuickCountActivity extends AppCompatActivity implements BarcodeScan,EquipmentQuickCountCompleted {


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
    private List<ToolhawkEquipment> assetList = new ArrayList<ToolhawkEquipment>();
    private List<ToolhawkEquipment> assetFoundList = new ArrayList<ToolhawkEquipment>();
    private List<ToolhawkEquipment> assetUnExpectedList = new ArrayList<ToolhawkEquipment>();
    ETSLocations etsLocation;
    ToolhawkEquipment toolhawkEquipment;
    private List<QuickCountAsset> QuickCountAssets = new ArrayList<QuickCountAsset>();
    QuickCount orgQuickCount;
    boolean newAfterSaveComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_count);

        initViews();
        initObj();
        initListeners();
        setupView();
        hideKeyboard();


    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvAssetOtherInfo = (TextView) findViewById(R.id.tvAssetOtherInfo);
        tvUnExpected = (TextView) findViewById(R.id.tvUnExpected);
        tvExpected = (TextView) findViewById(R.id.tvExpected);
        tvFound = (TextView) findViewById(R.id.tvFound);
        tvLocName = (TextView) findViewById(R.id.tvLocNameInspect);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        rvQuickCount = (RecyclerView) findViewById(R.id.rvQuickCount);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtnsQuickCount);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);

        taskType = getIntent().getStringExtra("taskType");
        locationCode = getIntent().getStringExtra("locationCode");
        newAfterSaveComplete = getIntent().getBooleanExtra("newAfterSaveComplete", false);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("Quick Count");

        etsLocation = DataManager.getInstance().getETSLocationsByCode(locationCode);

        if (null != etsLocation) {
            tvLocName.setText("" + etsLocation.getCode());
            tvAssetOtherInfo.setText("" + etsLocation.getDescription());
        }

        if (taskType.toLowerCase().startsWith("new")) {
            setupAssetListForNewCount();
        } else {
            setupAssetListForExistingCount();
        }

    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(LocationQuickCountActivity.this);
        orgQuickCount = DataManager.getInstance().getQuickCount(etsLocation.getCode());
    }


    private void initListeners() {
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);

        rvQuickCount.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(LocationQuickCountActivity.this, rvQuickCount, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setupView() {
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


    private void setupAssetListForExistingCount() {
        assetFoundList.clear();
        assetUnExpectedList.clear();

        if (null != DataManager.getInstance().getQuickCountAssetList(locationCode)) {
            QuickCountAssets = DataManager.getInstance().getQuickCountAssetList(locationCode).getQuickCountAssets();
        }

        for (int i = 0; i < QuickCountAssets.size(); i++) {

            if (null != DataManager.getInstance().getToolhawkEquipmentByID(QuickCountAssets.get(i).getAssetID())) {
                if (QuickCountAssets.get(i).isFound() && !QuickCountAssets.get(i).isUnExpected()) {
                    assetFoundList.add(DataManager.getInstance().getToolhawkEquipmentByID(QuickCountAssets.get(i).getAssetID()));
                } else if (!QuickCountAssets.get(i).isFound() && QuickCountAssets.get(i).isUnExpected()) {
                    assetUnExpectedList.add(DataManager.getInstance().getToolhawkEquipmentByID(QuickCountAssets.get(i).getAssetID()));
                }
            }
        }
        List<ToolhawkEquipment> list = DataManager.getInstance().getLocationEquipment(locationCode);
        if (null != list) {

            for (int j = 0; j < list.size(); j++) {
                if (!assetFoundList.contains(list.get(j)) && !assetUnExpectedList.contains(list.get(j))) {
                    assetList.add(list.get(j));
                }
            }
        }

        int expectedCount = list.size();
        tvExpected.setText("" + expectedCount);
        tvFound.setText("" + assetFoundList.size());
        tvUnExpected.setText("" + assetUnExpectedList.size());
        mAdapter = new QuickCountAdapter(LocationQuickCountActivity.this, assetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(LocationQuickCountActivity.this);
        rvQuickCount.setLayoutManager(mLayoutManager);
        rvQuickCount.setItemAnimator(new DefaultItemAnimator());
        rvQuickCount.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private void setupAssetListForNewCount() {
        assetFoundList.clear();
        assetUnExpectedList.clear();
        assetList = DataManager.getInstance().getAllToolhawkEquipmentForLocation(locationCode);
        tvExpected.setText("" + assetList.size());
        mAdapter = new QuickCountAdapter(LocationQuickCountActivity.this, assetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(LocationQuickCountActivity.this);
        rvQuickCount.setLayoutManager(mLayoutManager);
        rvQuickCount.setItemAnimator(new DefaultItemAnimator());
        rvQuickCount.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(LocationQuickCountActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(LocationQuickCountActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(LocationQuickCountActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationQuickCountActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(LocationQuickCountActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationQuickCountActivity.this);
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
                    ActivityCompat.requestPermissions(LocationQuickCountActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(LocationQuickCountActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(LocationQuickCountActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(LocationQuickCountActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationQuickCountActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(LocationQuickCountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

                case R.id.ivTick: {
                    QuickCount myQuickCount = new QuickCount();
                    RealmList<QuickCountAsset> myQuickCountAssets = new RealmList<QuickCountAsset>();
                    if (!taskType.toLowerCase().startsWith("new") || newAfterSaveComplete) {
                        myQuickCount.setID(orgQuickCount.getID());
                    } else {
                        myQuickCount.setID(0);
                    }
                    if (null != etsLocation.getCustomer()) {
                        myQuickCount.setCustomerID(etsLocation.getCustomer().getID());
                    }
                    myQuickCount.setAssetCode(etsLocation.getCode());
                    myQuickCount.setChanged(true);
                    myQuickCount.setCountType("Location");
                    myQuickCount.setComplete(false);
                    myQuickCount.setStatus(taskType.toUpperCase());
                    myQuickCount.setUserId(sharedPreferencesManager.getInt(SharedPreferencesManager.LOGGED_IN_USERID));
                    String format = null;
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                        format = simpleDateFormat.format(new Date());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    myQuickCount.setQuickCountDate(format);
                    for (int i = 0; i < assetFoundList.size(); i++) {
                        QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                        myQuickAssetCount.setFound(true);
                        myQuickAssetCount.setUnExpected(false);
                        myQuickAssetCount.setAssetID(assetFoundList.get(i).getID());
                        myQuickAssetCount.setAssetCode(assetFoundList.get(i).getCode());
                        myQuickCountAssets.add(myQuickAssetCount);
                    }

                    for (int i = 0; i < assetUnExpectedList.size(); i++) {
                        QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                        myQuickAssetCount.setFound(false);
                        myQuickAssetCount.setUnExpected(true);
                        myQuickAssetCount.setAssetID(assetUnExpectedList.get(i).getID());
                        myQuickAssetCount.setAssetCode(assetUnExpectedList.get(i).getCode());
                        myQuickCountAssets.add(myQuickAssetCount);
                    }
                    myQuickCount.setQuickCountAssets(myQuickCountAssets);
                    if (newAfterSaveComplete) {
                        DataManager.getInstance().deleteQuickCountResult(orgQuickCount.getAssetCode());
                    }
                    DataManager.getInstance().saveQuickCountResult(myQuickCount);

                    showToast("Quick Count Saved!");
                    sendMessage("finish");
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
                            if (!assetFoundList.contains(toolhawkEquipment)) {
                                if (isfound) {
                                    ToolhawkEquipment equipment = DataManager.getInstance().isAssetParent(toolhawkEquipment.getCode());
                                    if (null==equipment) {
                                        mAdapter.removeAt(assetPos);
                                        showToast("Asset Found!");
                                        assetFoundList.add(toolhawkEquipment);
                                        tvFound.setText("" + assetFoundList.size());
                                        etBarcode.setText("");
                                        isfound = false;
                                        if (tvExpected.getText().toString().equals(tvFound.getText().toString())) {
                                            QuickCount myQuickCount = new QuickCount();
                                            RealmList<QuickCountAsset> myQuickCountAssets = new RealmList<QuickCountAsset>();
                                            if (!taskType.toLowerCase().startsWith("new") || newAfterSaveComplete) {
                                                myQuickCount.setID(orgQuickCount.getID());
                                            } else {
                                                myQuickCount.setID(0);
                                            }
                                            myQuickCount.setAssetCode(etsLocation.getCode());
                                            myQuickCount.setChanged(true);
                                            myQuickCount.setComplete(true);
                                            myQuickCount.setCountType("Location");
                                            myQuickCount.setStatus(taskType.toUpperCase());
                                            myQuickCount.setUserId(sharedPreferencesManager.getInt(SharedPreferencesManager.LOGGED_IN_USERID));


                                            String format = null;
                                            try {
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                                                format = simpleDateFormat.format(new Date());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            myQuickCount.setQuickCountDate(format);
                                            if (null != etsLocation.getCustomer()) {
                                                myQuickCount.setCustomerID(etsLocation.getCustomer().getID());
                                            }
                                            for (int i = 0; i < assetFoundList.size(); i++) {
                                                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                                                myQuickAssetCount.setFound(true);
                                                myQuickAssetCount.setUnExpected(false);
                                                myQuickAssetCount.setAssetID(assetFoundList.get(i).getID());
                                                myQuickAssetCount.setAssetCode(assetFoundList.get(i).getCode());
                                                myQuickCountAssets.add(myQuickAssetCount);
                                            }

                                            for (int i = 0; i < assetUnExpectedList.size(); i++) {
                                                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                                                myQuickAssetCount.setFound(false);
                                                myQuickAssetCount.setUnExpected(true);
                                                myQuickAssetCount.setAssetID(assetUnExpectedList.get(i).getID());
                                                myQuickAssetCount.setAssetCode(assetUnExpectedList.get(i).getCode());
                                                myQuickCountAssets.add(myQuickAssetCount);
                                            }
                                            myQuickCount.setQuickCountAssets(myQuickCountAssets);
                                            if (newAfterSaveComplete) {
                                                DataManager.getInstance().deleteQuickCountResult(orgQuickCount.getAssetCode());
                                            }
                                            DataManager.getInstance().saveQuickCountResult(myQuickCount);

                                            showToast("Quick Count Complete!");
                                            sendMessage("finish");
                                            finish();
                                        }
                                    } else {
                                        final int finalAssetPos = assetPos;
                                        new AlertDialog.Builder(LocationQuickCountActivity.this)
                                                .setTitle("Quick Count")
                                                .setMessage(toolhawkEquipment.getCode() + " is a Parent do you want to Count the childern?")
                                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        setInterfaceEquipmentQuickCountComplete();
                                                        Intent in = new Intent(LocationQuickCountActivity.this,EquipmentQuickCountActivity.class);
                                                        in.putExtra("taskType",taskType);
                                                        in.putExtra("eqCode",toolhawkEquipment.getCode());
                                                        startActivity(in);
                                                    }
                                                })
                                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        mAdapter.removeAt(finalAssetPos);
                                                        showToast("Asset Found!");
                                                        assetFoundList.add(toolhawkEquipment);
                                                        tvFound.setText("" + assetFoundList.size());
                                                        etBarcode.setText("");
                                                        if (tvExpected.getText().toString().equals(tvFound.getText().toString())) {
                                                            QuickCount myQuickCount = new QuickCount();
                                                            RealmList<QuickCountAsset> myQuickCountAssets = new RealmList<QuickCountAsset>();
                                                            if (!taskType.toLowerCase().startsWith("new") || newAfterSaveComplete) {
                                                                myQuickCount.setID(orgQuickCount.getID());
                                                            } else {
                                                                myQuickCount.setID(0);
                                                            }
                                                            myQuickCount.setAssetCode(etsLocation.getCode());
                                                            myQuickCount.setChanged(true);
                                                            myQuickCount.setComplete(true);
                                                            myQuickCount.setCountType("Location");
                                                            myQuickCount.setStatus(taskType.toUpperCase());
                                                            myQuickCount.setUserId(sharedPreferencesManager.getInt(SharedPreferencesManager.LOGGED_IN_USERID));


                                                            String format = null;
                                                            try {
                                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                                                                format = simpleDateFormat.format(new Date());
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                            myQuickCount.setQuickCountDate(format);
                                                            if (null != etsLocation.getCustomer()) {
                                                                myQuickCount.setCustomerID(etsLocation.getCustomer().getID());
                                                            }
                                                            for (int i = 0; i < assetFoundList.size(); i++) {
                                                                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                                                                myQuickAssetCount.setFound(true);
                                                                myQuickAssetCount.setUnExpected(false);
                                                                myQuickAssetCount.setAssetID(assetFoundList.get(i).getID());
                                                                myQuickAssetCount.setAssetCode(assetFoundList.get(i).getCode());
                                                                myQuickCountAssets.add(myQuickAssetCount);
                                                            }

                                                            for (int i = 0; i < assetUnExpectedList.size(); i++) {
                                                                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                                                                myQuickAssetCount.setFound(false);
                                                                myQuickAssetCount.setUnExpected(true);
                                                                myQuickAssetCount.setAssetID(assetUnExpectedList.get(i).getID());
                                                                myQuickAssetCount.setAssetCode(assetUnExpectedList.get(i).getCode());
                                                                myQuickCountAssets.add(myQuickAssetCount);
                                                            }
                                                            myQuickCount.setQuickCountAssets(myQuickCountAssets);
                                                            if (newAfterSaveComplete) {
                                                                DataManager.getInstance().deleteQuickCountResult(orgQuickCount.getAssetCode());
                                                            }
                                                            DataManager.getInstance().saveQuickCountResult(myQuickCount);

                                                            showToast("Quick Count Complete!");
                                                            sendMessage("finish");
                                                            finish();
                                                        }
                                                    }
                                                })
                                                .show();
                                    }

                                } else {
                                    new AlertDialog.Builder(LocationQuickCountActivity.this)
                                            .setTitle("Quick Count")
                                            .setMessage(toolhawkEquipment.getCode() + " doesn't belong to " + tvLocName.getText().toString() + ", do you want to Continue?")
                                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (!assetUnExpectedList.contains(toolhawkEquipment)) {
                                                        assetUnExpectedList.add(toolhawkEquipment);
                                                        tvUnExpected.setText("" + assetUnExpectedList.size());
                                                        etBarcode.setText("");
                                                        hideKeyboard();
                                                    } else {
                                                        showToast("Already added to Unexpected Asset(s)!");
                                                    }
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
                                showToast("Already added to Found Asset(s)!");
                                etBarcode.setText("");
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
            if (!assetFoundList.contains(toolhawkEquipment)) {
                if (isfound) {
                    ToolhawkEquipment equipment = DataManager.getInstance().isAssetParent(toolhawkEquipment.getCode());
                    if (null==equipment) {
                        mAdapter.removeAt(assetPos);
                        showToast("Asset Found!");
                        assetFoundList.add(toolhawkEquipment);
                        tvFound.setText("" + assetFoundList.size());
                        etBarcode.setText("");
                        isfound = false;
                        if (tvExpected.getText().toString().equals(tvFound.getText().toString())) {
                            QuickCount myQuickCount = new QuickCount();
                            RealmList<QuickCountAsset> myQuickCountAssets = new RealmList<QuickCountAsset>();
                            if (taskType.toLowerCase().startsWith("ne")) {
                                myQuickCount.setID(0);
                            } else {
                                myQuickCount.setID(orgQuickCount.getID());
                            }
                            myQuickCount.setAssetCode(etsLocation.getCode());
                            myQuickCount.setChanged(true);
                            myQuickCount.setCountType("Location");
                            myQuickCount.setComplete(true);
                            myQuickCount.setStatus(taskType.toUpperCase());
                            myQuickCount.setUserId(sharedPreferencesManager.getInt(SharedPreferencesManager.LOGGED_IN_USERID));
                            String format = null;
                            try {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                                format = simpleDateFormat.format(new Date());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            myQuickCount.setQuickCountDate(format);
                            for (int i = 0; i < assetFoundList.size(); i++) {
                                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                                myQuickAssetCount.setFound(true);
                                myQuickAssetCount.setUnExpected(false);
                                myQuickAssetCount.setAssetID(assetFoundList.get(i).getID());
                                myQuickAssetCount.setAssetCode(assetFoundList.get(i).getCode());
                                myQuickCountAssets.add(myQuickAssetCount);
                            }

                            for (int i = 0; i < assetUnExpectedList.size(); i++) {
                                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                                myQuickAssetCount.setFound(false);
                                myQuickAssetCount.setUnExpected(true);
                                myQuickAssetCount.setAssetID(assetUnExpectedList.get(i).getID());
                                myQuickAssetCount.setAssetCode(assetUnExpectedList.get(i).getCode());
                                myQuickCountAssets.add(myQuickAssetCount);
                            }
                            if (null != etsLocation.getCustomer()) {
                                myQuickCount.setCustomerID(etsLocation.getCustomer().getID());
                            }
                            myQuickCount.setQuickCountAssets(myQuickCountAssets);
                            DataManager.getInstance().saveQuickCountResult(myQuickCount);

                            showToast("Quick Count Complete!");
                            sendMessage("finish");
                            finish();
                        }
                    } else {

                        final int finalAssetPos = assetPos;
                        new AlertDialog.Builder(LocationQuickCountActivity.this)
                                .setTitle("Quick Count")
                                .setMessage(toolhawkEquipment.getCode() + " is a Parent do you want to Count the childern?")
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        setInterfaceEquipmentQuickCountComplete();
                                        Intent in = new Intent(LocationQuickCountActivity.this,EquipmentQuickCountActivity.class);
                                        in.putExtra("pos", finalAssetPos);
                                        in.putExtra("taskType",taskType);
                                        in.putExtra("eqCode",toolhawkEquipment.getCode());
                                        startActivity(in);
                                    }
                                })
                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mAdapter.removeAt(finalAssetPos);
                                        showToast("Asset Found!");
                                        assetFoundList.add(toolhawkEquipment);
                                        tvFound.setText("" + assetFoundList.size());
                                        etBarcode.setText("");
                                        if (tvExpected.getText().toString().equals(tvFound.getText().toString())) {
                                            QuickCount myQuickCount = new QuickCount();
                                            RealmList<QuickCountAsset> myQuickCountAssets = new RealmList<QuickCountAsset>();
                                            if (!taskType.toLowerCase().startsWith("new") || newAfterSaveComplete) {
                                                myQuickCount.setID(orgQuickCount.getID());
                                            } else {
                                                myQuickCount.setID(0);
                                            }
                                            myQuickCount.setAssetCode(etsLocation.getCode());
                                            myQuickCount.setChanged(true);
                                            myQuickCount.setCountType("Location");
                                            myQuickCount.setComplete(true);
                                            myQuickCount.setStatus(taskType.toUpperCase());
                                            myQuickCount.setUserId(sharedPreferencesManager.getInt(SharedPreferencesManager.LOGGED_IN_USERID));


                                            String format = null;
                                            try {
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                                                format = simpleDateFormat.format(new Date());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            myQuickCount.setQuickCountDate(format);
                                            if (null != etsLocation.getCustomer()) {
                                                myQuickCount.setCustomerID(etsLocation.getCustomer().getID());
                                            }
                                            for (int i = 0; i < assetFoundList.size(); i++) {
                                                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                                                myQuickAssetCount.setFound(true);
                                                myQuickAssetCount.setUnExpected(false);
                                                myQuickAssetCount.setAssetID(assetFoundList.get(i).getID());
                                                myQuickAssetCount.setAssetCode(assetFoundList.get(i).getCode());
                                                myQuickCountAssets.add(myQuickAssetCount);
                                            }

                                            for (int i = 0; i < assetUnExpectedList.size(); i++) {
                                                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                                                myQuickAssetCount.setFound(false);
                                                myQuickAssetCount.setUnExpected(true);
                                                myQuickAssetCount.setAssetID(assetUnExpectedList.get(i).getID());
                                                myQuickAssetCount.setAssetCode(assetUnExpectedList.get(i).getCode());
                                                myQuickCountAssets.add(myQuickAssetCount);
                                            }
                                            myQuickCount.setQuickCountAssets(myQuickCountAssets);
                                            if (newAfterSaveComplete) {
                                                DataManager.getInstance().deleteQuickCountResult(orgQuickCount.getAssetCode());
                                            }
                                            DataManager.getInstance().saveQuickCountResult(myQuickCount);

                                            showToast("Quick Count Complete!");
                                            sendMessage("finish");
                                            finish();
                                        }
                                    }
                                })
                                .show();
                    }

                } else {
                    new AlertDialog.Builder(LocationQuickCountActivity.this)
                            .setTitle("Quick Count")
                            .setMessage(toolhawkEquipment.getCode() + " doesn't belong to " + tvLocName.getText().toString() + ", do you want to Continue?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!assetUnExpectedList.contains(toolhawkEquipment)) {
                                        assetUnExpectedList.add(toolhawkEquipment);
                                        tvUnExpected.setText("" + assetUnExpectedList.size());
                                    } else {
                                        showToast("Already added to Unexpected Asset(s)!");
                                    }

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
                showToast("Already added to Found Asset(s)!");
                etBarcode.setText("");
            }
        } else {
            showToast("No equipment found!");
        }

    }

    private void setInterfaceEquipmentQuickCountComplete() {
        EquipmentQuickCountActivity.equipmentQuickCountCompleted = this;
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

    @Override
    public void EquipmentQuickCountComplete(ToolhawkEquipment toolhawkEquipment , int pos) {

        mAdapter.removeAt(pos);
        showToast("Asset Found!");
        assetFoundList.add(toolhawkEquipment);
        tvFound.setText("" + assetFoundList.size());
        etBarcode.setText("");
        if (tvExpected.getText().toString().equals(tvFound.getText().toString())) {
            QuickCount myQuickCount = new QuickCount();
            RealmList<QuickCountAsset> myQuickCountAssets = new RealmList<QuickCountAsset>();
            if (taskType.toLowerCase().startsWith("ne")) {
                myQuickCount.setID(0);
            } else {
                myQuickCount.setID(orgQuickCount.getID());
            }
            myQuickCount.setAssetCode(etsLocation.getCode());
            myQuickCount.setChanged(true);
            myQuickCount.setCountType("Location");
            myQuickCount.setComplete(true);
            myQuickCount.setStatus(taskType.toUpperCase());
            myQuickCount.setUserId(sharedPreferencesManager.getInt(SharedPreferencesManager.LOGGED_IN_USERID));
            String format = null;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                format = simpleDateFormat.format(new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
            myQuickCount.setQuickCountDate(format);
            for (int i = 0; i < assetFoundList.size(); i++) {
                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                myQuickAssetCount.setFound(true);
                myQuickAssetCount.setUnExpected(false);
                myQuickAssetCount.setAssetID(assetFoundList.get(i).getID());
                myQuickAssetCount.setAssetCode(assetFoundList.get(i).getCode());
                myQuickCountAssets.add(myQuickAssetCount);
            }

            for (int i = 0; i < assetUnExpectedList.size(); i++) {
                QuickCountAsset myQuickAssetCount = new QuickCountAsset();
                myQuickAssetCount.setFound(false);
                myQuickAssetCount.setUnExpected(true);
                myQuickAssetCount.setAssetID(assetUnExpectedList.get(i).getID());
                myQuickAssetCount.setAssetCode(assetUnExpectedList.get(i).getCode());
                myQuickCountAssets.add(myQuickAssetCount);
            }
            if (null != etsLocation.getCustomer()) {
                myQuickCount.setCustomerID(etsLocation.getCustomer().getID());
            }
            myQuickCount.setQuickCountAssets(myQuickCountAssets);
            DataManager.getInstance().saveQuickCountResult(myQuickCount);

            showToast("Quick Count Complete!");
            sendMessage("finish");
            finish();
        }
    }
}

