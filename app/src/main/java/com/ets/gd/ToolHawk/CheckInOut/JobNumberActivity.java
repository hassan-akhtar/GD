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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Adapters.JobNumberAdapter;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class JobNumberActivity extends AppCompatActivity  implements BarcodeScan{


    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvScanType;
    TextView tvCount, tvCountSupportText, tvTaskName;
    RelativeLayout rlForwardArrow;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    String taskType, department, returningUser;
    ImageView ivBack, ivTick;
    RecyclerView rvList;
    private List<JobNumber> jobNumberList = new ArrayList<JobNumber>();
    JobNumberAdapter mAdapter;
    boolean isUser = false;
    RelativeLayout rlBottomSheetMove;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    SharedPreferencesManager sharedPreferencesManager;
    JobNumber jobNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_number);

        initViews();
        initObj();
        initListeners();
        setupView();
        setupJobNumbers();
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

        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCountSupportText = (TextView) findViewById(R.id.tvCountSupportText);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        rlForwardArrow = (RelativeLayout) findViewById(R.id.rlForwardArrow);
        rlBottomSheetMove  = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        department = getIntent().getStringExtra("department");
        isUser = getIntent().getBooleanExtra("isUser",false);
        returningUser = getIntent().getStringExtra("returningUser");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
        tvScanType.setText("Select / Scan Job Number");
        tvDepartment.setText("" + department);
        tvUnderText.setText("Scan or Enter Job Number");
        tvCount.setVisibility(View.GONE);
        tvCountSupportText.setText("Skip this step if you don't want to assign any Job number");
        tvTaskName.setText("SKIP JOB NUMBER");

        if(isUser){
            rlBottomSheetMove.setVisibility(View.VISIBLE);
        }else{
            rlBottomSheetMove.setVisibility(View.GONE);
        }
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(JobNumberActivity.this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);

        rvList.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(JobNumberActivity.this, rvList, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent in = new Intent(JobNumberActivity.this, CheckoutAssetActivity.class);
                in.putExtra("taskType", taskType);
                in.putExtra("department", department);
                in.putExtra("returningUser", returningUser);
                in.putExtra("isUser", isUser);
                in.putExtra("JobNumber", jobNumberList.get(position).getCode());
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

    private void setupJobNumbers() {

        jobNumberList = DataManager.getInstance().getAllJobNumberList();

        mAdapter = new JobNumberAdapter(JobNumberActivity.this, jobNumberList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(JobNumberActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }


    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
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
    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {

                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();
                    } else {
                        jobNumber  =DataManager.getInstance().getJobNumber(etBarcode.getText().toString().trim());
                        if (null!=jobNumber) {
                            Intent in = new Intent(JobNumberActivity.this, CheckoutAssetActivity.class);
                            in.putExtra("taskType", taskType);
                            in.putExtra("department", department);
                            in.putExtra("returningUser", returningUser);
                            in.putExtra("JobNumber", jobNumber.getCode());
                            startActivity(in);
                        } else {
                            showToast("No Job Number Found!");
                        }

                    }

                    break;
                }

                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.rlForwardArrow: {
                    Intent in = new Intent(JobNumberActivity.this, CheckoutAssetActivity.class);
                    in.putExtra("taskType", taskType);
                    in.putExtra("department", department);
                    in.putExtra("isUser", isUser);
                    in.putExtra("returningUser", returningUser);
                    startActivity(in);
                    break;
                }
            }
        }

    };


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(JobNumberActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(JobNumberActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(JobNumberActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(JobNumberActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(JobNumberActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(JobNumberActivity.this);
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
                    ActivityCompat.requestPermissions(JobNumberActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(JobNumberActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(JobNumberActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(JobNumberActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(JobNumberActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(JobNumberActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

        jobNumber  =DataManager.getInstance().getJobNumber(message);
        if (null!=jobNumber) {
            Intent in = new Intent(JobNumberActivity.this, CheckoutAssetActivity.class);
            in.putExtra("taskType", taskType);
            in.putExtra("department", department);
            in.putExtra("returningUser", returningUser);
            in.putExtra("JobNumber", jobNumber.getCode());
            startActivity(in);
        } else {
            showToast("No Job Number Found!");
        }
    }
}


