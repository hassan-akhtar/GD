package com.ets.gd.ToolHawk.CheckInOut;

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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
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
import com.ets.gd.Models.User;
import com.ets.gd.NetworkLayer.ResponseDTOs.MobileUser;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.ToolhawkScanActivityWithList;
import com.ets.gd.ToolHawk.Adapters.UserAdapter;
import com.ets.gd.ToolHawk.Move.MoveAssetActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements BarcodeScan{

    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvScanType;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    String taskType, department;
    ImageView ivBack, ivTick;
    RecyclerView rvList;
    UserAdapter mAdapter;
    private List<MobileUser> userList = new ArrayList<MobileUser>();
    MobileUser mobileUser;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_user);

        initViews();
        initObj();
        initListeners();
        setupView();
        setupUsers();
        hideKeyboard();

    }

    private void initViews() {
        rvList = (RecyclerView) findViewById(R.id.rvList);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtnsQuickCount);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvScanType = (TextView) findViewById(R.id.tvScanType);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        department = getIntent().getStringExtra("department");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
        tvScanType.setText("Select User from List or Enter / Scan User ID ");
        tvDepartment.setText("" + department);
        tvUnderText.setText("Enter/Scan ID of user");
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(UserActivity.this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);

        rvList.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(UserActivity.this, rvList, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent in = new Intent(UserActivity.this, JobNumberActivity.class);
                in.putExtra("taskType", taskType);
                in.putExtra("department", department);
                in.putExtra("isUser", true);
                in.putExtra("returningUser", userList.get(position).getUserName());
                startActivity(in);

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

    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void setupUsers() {

        userList = DataManager.getInstance().getAllMobileUserList();
        mAdapter = new UserAdapter(UserActivity.this, userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(UserActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {

                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();
                    } else {
                    mobileUser = DataManager.getInstance().getMobileUser(etBarcode.getText().toString());
                        if (null!=mobileUser) {
                            Intent in = new Intent(UserActivity.this, JobNumberActivity.class);
                            in.putExtra("taskType", taskType);
                            in.putExtra("department", department);
                            in.putExtra("isUser", true);
                            in.putExtra("returningUser", mobileUser.getUserName());
                            startActivity(in);
                        } else {
                            showToast("No user found!");
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

    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(UserActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(UserActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(UserActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
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
                    ActivityCompat.requestPermissions(UserActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(UserActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(UserActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(UserActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

        mobileUser = DataManager.getInstance().getMobileUser(message);
        if (null!=mobileUser) {
            Intent in = new Intent(UserActivity.this, JobNumberActivity.class);
            in.putExtra("taskType", taskType);
            in.putExtra("department", department);
            in.putExtra("isUser", true);
            in.putExtra("returningUser", mobileUser.getUserName());
            startActivity(in);
        } else {
            showToast("No user found!");
        }
    }
}


