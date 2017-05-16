package com.ets.gd.Activities.FireBug.Move;

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

import com.ets.gd.Activities.Scan.BarcodeScanActivity;
import com.ets.gd.Activities.Scan.CommonFirebugScanActivity;
import com.ets.gd.Adapters.ScannedAssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Interfaces.LocationMoved;
import com.ets.gd.Models.Barcode;
import com.ets.gd.Models.Move;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class SelectLocationActivity extends AppCompatActivity implements BarcodeScan{


    ImageView ivBack, ivChangeCompany, ivTick;
    EditText etBarcode;
    ScannedAssetsAdapter mAdapter;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvUnderText, tvBarcodeTitle, tvBarcodeValue;
    Button btnScan, btnCross;
    RecyclerView rlLocs;
    SharedPreferencesManager sharedPreferencesManager;
    List<Locations> locList = new ArrayList<Locations>();
    Context mContext;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String location, taskType, assetLoc;
    int cusID;
    public static LocationMoved locationMoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

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
        tbTitleBottom.setText("Select Location");
        tvUnderText.setText("Enter Location ID");
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(SelectLocationActivity.this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBarcodeBroadcastReceiver,
                new IntentFilter("barcode-scanned"));
        mContext = this;
        location = getIntent().getStringExtra("compName");
        taskType = getIntent().getStringExtra("type");
        assetLoc = getIntent().getStringExtra("assetLoc");
        cusID = getIntent().getIntExtra("cusID", 0);
        sharedPreferencesManager.setString(SharedPreferencesManager.CURRENT_SELECT_LOC_TYPE,taskType);
        tvCompanyValue.setText(location);
        hideKeyboard();
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        setupLocList();
        mAdapter = new ScannedAssetsAdapter(locList, getApplicationContext(), "loc");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rlLocs.setLayoutManager(mLayoutManager);
        rlLocs.setItemAnimator(new DefaultItemAnimator());
        rlLocs.setAdapter(mAdapter);
    }


    private void setupLocList() {

        locList.clear();

        List<Locations> locationsRealmList = new RealmList<Locations>();
        locationsRealmList = DataManager.getInstance().getAllCompanyLocations(DataManager.getInstance().getCustomerByCode(tvCompanyValue.getText().toString()).getID());

        for (int k = 0; k < locationsRealmList.size(); k++) {
            if (!locationsRealmList.get(k).isAdded()) {
                locList.add(locationsRealmList.get(k));
            }
        }

        if (0 == locList.size()) {
            Toast.makeText(SelectLocationActivity.this, "No Location(s) found for " + tvCompanyValue.getText().toString(), Toast.LENGTH_SHORT).show();
        }

    }


    public void hideKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);

        rlLocs.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(SelectLocationActivity.this, rlLocs, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (taskType.startsWith("ins")) {
                    if (!assetLoc.trim().equals(String.valueOf(locList.get(position).getCode().trim()))) {
                       // sendMessage(String.valueOf(locList.get(position).getCode()), locList.get(position).getID());
                        sharedPreferencesManager.setString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME, tvCompanyValue.getText().toString());
                        locationMoved.MoveLocation(new Move(locList.get(position).getID(),cusID));
                        finish();
                    } else {
                        Toast.makeText(SelectLocationActivity.this, "You can not select same location", Toast.LENGTH_SHORT).show();
                        Toast.makeText(SelectLocationActivity.this, "Please select any other location", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    sendMessage(String.valueOf(locList.get(position).getCode()), locList.get(position).getID());
                    finish();
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
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.btnScan: {
                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();


                    } else {
                        Locations loc = DataManager.getInstance().getLocation(etBarcode.getText().toString().trim());
                        if (null != loc) {
                            if (taskType.startsWith("ins")) {
                                if (!assetLoc.trim().equals(String.valueOf(loc.getCode().trim()))) {
                                    //sendMessage(String.valueOf(loc.getCode()), loc.getID());
                                    sharedPreferencesManager.setString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME, tvCompanyValue.getText().toString());
                                    locationMoved.MoveLocation(new Move(loc.getID(),cusID));
                                    finish();
                                } else {
                                    Toast.makeText(SelectLocationActivity.this, "You can not select same location", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SelectLocationActivity.this, "Please select any other location", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                sendMessage(String.valueOf(loc.getCode()), loc.getID());
                                finish();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_LONG).show();
                        }

                    }
                    break;
                }
            }
        }

    };


    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(SelectLocationActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", "loc");
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(SelectLocationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SelectLocationActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectLocationActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(SelectLocationActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectLocationActivity.this);
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
                    ActivityCompat.requestPermissions(SelectLocationActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(SelectLocationActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", "loc");
                startActivity(in);
            }
        }
    }

    private void sendMessage(String msg, int locID) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("moveToLoc");
        intent.putExtra("message", msg);
        intent.putExtra("locID", locID);
        intent.putExtra("cusID", cusID);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sharedPreferencesManager.setBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION, true);
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(SelectLocationActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", "loc");
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SelectLocationActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectLocationActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(SelectLocationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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
    private final BroadcastReceiver mBarcodeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           /* String message = intent.getStringExtra("message");
            String task = intent.getStringExtra("taskType");
            if (task.startsWith("loc")) {

                try {
                    Locations loc = DataManager.getInstance().getLocation(message);
                    if (null != loc) {
                        if (taskType.startsWith("ins")) {
                            if (!assetLoc.trim().equals(String.valueOf(loc.getCode().trim()))) {
                                sendMessage(String.valueOf(loc.getCode()), loc.getID());
                                finish();
                            } else {
                                Toast.makeText(SelectLocationActivity.this, "You can not select same location", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SelectLocationActivity.this, "Please select any other location", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            sendMessage(String.valueOf(loc.getCode()), loc.getID());
                            finish();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Invalid Int (barcode)", Toast.LENGTH_LONG).show();
                }
            }*/

        }
    };

    @Override
    public void BarcodeScanned(Barcode barcode) {

        String message = barcode.getMessage();
        String task = barcode.getTask();
        if (task.startsWith("loc")) {

            try {
                Locations loc = DataManager.getInstance().getLocation(message);
                if (null != loc) {
                    if (taskType.startsWith("ins")) {
                        if (!assetLoc.trim().equals(String.valueOf(loc.getCode().trim()))) {
                          //  sendMessage(String.valueOf(loc.getCode()), loc.getID());
                            sharedPreferencesManager.setString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME, tvCompanyValue.getText().toString());
                            locationMoved.MoveLocation(new Move(loc.getID(),cusID));
                            finish();
                        } else {
                            Toast.makeText(SelectLocationActivity.this, "You can not select same location", Toast.LENGTH_SHORT).show();
                            Toast.makeText(SelectLocationActivity.this, "Please select any other location", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        sendMessage(String.valueOf(loc.getCode()), loc.getID());
                        finish();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Invalid Int (barcode)", Toast.LENGTH_LONG).show();
            }
        }

    }
}
