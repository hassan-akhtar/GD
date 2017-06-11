package com.ets.gd.ToolHawk.CheckInOut;

import android.Manifest;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Move.LocationSelectionActivity;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.ResponseDTOs.DashboardStats;
import com.ets.gd.NetworkLayer.ResponseDTOs.Department;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.ToolhawkScanActivityWithList;
import com.ets.gd.ToolHawk.Adapters.ScannedAssetsToolhawkAdapter;
import com.ets.gd.ToolHawk.Move.MoveAssetActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class CheckoutAssetActivity extends AppCompatActivity implements BarcodeScan {

    TextView tvBarcodeValue, tbTitleTop, tbTitleBottom, tvBarcodeTitle, tvUnderText, tvDepartment, tvReturningUser, tvJobNumberCode;
    TextView tvCount, tvCountSupportText, tvTaskName;
    TextView tvAssetTextandCount, tvAssets, tvUser, tvTitle;
    RelativeLayout rlForwardArrow, rlCheckInOut, rlListArea, rlBottomSheetMove;
    Button btnCross, btnScan, btnViewAllAssets;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    LinearLayout llJobNumber, llUser;
    RecyclerView rvList;
    String taskType, department, returningUser, JobNumber;
    ImageView ivBack, ivTick;
    boolean isfinalCheckout = false, isUser = false;
    Department dep;
    ScannedAssetsToolhawkAdapter mAdapter;
    SharedPreferencesManager sharedPreferencesManager;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private List<ToolhawkEquipment> equipmentList = new ArrayList<ToolhawkEquipment>();
    ToolhawkEquipment toolhawkEquipment;
    Context mContext;
    String[] assetNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_asset);


        initViews();
        initObj();
        initListeners();
        setupView();

    }

    private void initViews() {

        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        btnScan = (Button) findViewById(R.id.btnScan);
        btnViewAllAssets = (Button) findViewById(R.id.btnViewAllAssets);
        llbtns = (LinearLayout) findViewById(R.id.llbtnsQuickCount);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tvReturningUser = (TextView) findViewById(R.id.tvReturningUserName);
        tvJobNumberCode = (TextView) findViewById(R.id.tvJobNumberCode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        llJobNumber = (LinearLayout) findViewById(R.id.llJobNumber);
        llUser = (LinearLayout) findViewById(R.id.llUser);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        rlListArea = (RelativeLayout) findViewById(R.id.rlScanArea);
        rlBottomSheetMove = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCountSupportText = (TextView) findViewById(R.id.tvCountSupportText);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        rlForwardArrow = (RelativeLayout) findViewById(R.id.rlForwardArrow);

        rlCheckInOut = (RelativeLayout) findViewById(R.id.rlCheckInOut);
        tvAssetTextandCount = (TextView) findViewById(R.id.tvAssetTextandCount);
        tvAssets = (TextView) findViewById(R.id.tvAssets);
        tvUser = (TextView) findViewById(R.id.tvUser);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        taskType = getIntent().getStringExtra("taskType");
        department = getIntent().getStringExtra("department");
        returningUser = getIntent().getStringExtra("returningUser");
        JobNumber = getIntent().getStringExtra("JobNumber");
        isUser = getIntent().getBooleanExtra("isUser", false);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
        tvDepartment.setText("" + department);
        tvUnderText.setText("Scan or Enter asset ID");
        tvReturningUser.setText("" + returningUser);
        tvJobNumberCode.setText("" + JobNumber);

        dep = DataManager.getInstance().getDepartmentByCode(department);

        tvCount.setText("2");
        if (tbTitleBottom.getText().toString().toLowerCase().startsWith("check out")) {
            tvAssetTextandCount.setText("Checking out 1 Asset");
        } else {
            tvAssetTextandCount.setText("Checking In 1 Asset");
        }

        tvCountSupportText.setText("Asset Selected To " + tbTitleBottom.getText().toString());
        tvTaskName.setText(" " + tbTitleBottom.getText().toString().toUpperCase());


        if (null == JobNumber) {
            llJobNumber.setVisibility(View.GONE);
        } else {
            llJobNumber.setVisibility(View.VISIBLE);
        }


        if (!isUser) {
            llUser.setVisibility(View.GONE);
        }
    }

    private void initObj() {
        hideKeyboard();
        sharedPreferencesManager = new SharedPreferencesManager(CheckoutAssetActivity.this);
        mContext = this;
        mAdapter = new ScannedAssetsToolhawkAdapter(CheckoutAssetActivity.this, equipmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CheckoutAssetActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initListeners() {
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        btnViewAllAssets.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);

        rvList.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getApplicationContext(), rvList, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);
                alertDialogBuilder.setTitle("Remove Asset");
                alertDialogBuilder.setMessage("Are you sure you want to remove " + equipmentList.get(position).getCode());
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("REMOVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        mAdapter.removeAt(position);
                                            if (0 != equipmentList.size()) {
                                                tvCount.setText("" + equipmentList.size());
                                            } else {
                                                rlBottomSheetMove.setVisibility(View.GONE);
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

    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                etBarcode.getWindowToken(), 0);
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        llbtns.setVisibility(View.GONE);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {
                    if ("".equals(etBarcode.getText().toString().trim())) {
                        checkCameraPermission();
                    } else {
                        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(etBarcode.getText().toString());
                        if (null != toolhawkEquipment) {
                            if (dep.getCode().toLowerCase().equals(toolhawkEquipment.getDepartment().getCode().toLowerCase())) {
                                if (!equipmentList.contains(toolhawkEquipment)) {
                                    equipmentList.add(toolhawkEquipment);
                                    rlBottomSheetMove.setVisibility(View.VISIBLE);
                                    tvCount.setText(""+equipmentList.size());
                                    mAdapter.notifyDataSetChanged();
                                    etBarcode.setText("");
                                    hideKeyboard();
                                } else {
                                    showToast("This Asset is already added");
                                }
                            } else {
                                showToast("This Asset is not found in " + department);
                            }
                        } else {
                            showToast("Asset not found!");
                        }
                    }
                    break;
                }

                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.btnViewAllAssets: {

                    assetNames = new String[equipmentList.size()];

                    for (int i = 0; i < equipmentList.size(); i++) {
                        assetNames[i] = equipmentList.get(i).getCode();
                    }
                    showAssetList();
                    break;
                }


                case R.id.rlForwardArrow: {
                    if (!isfinalCheckout) {
                        rlCheckInOut.setVisibility(View.VISIBLE);
                        rlListArea.setVisibility(View.GONE);
                        rvList.setVisibility(View.GONE);
                        if(tbTitleBottom.getText().toString().contains("out")){
                            tvAssetTextandCount.setText("Checking out "+ equipmentList.size()+" Asset(s)");
                        }else{
                            tvAssetTextandCount.setText("Checking In "+ equipmentList.size()+" Asset(s)");
                        }

                        if (!isUser) {
                            tvUser.setText(""+JobNumber);
                            tvTitle.setText("JOB NUMBER");
                        }else{
                            tvUser.setText(""+returningUser);
                            tvTitle.setText("USER");
                        }


                        if(1==equipmentList.size()){
                            tvAssets.setText(""+ equipmentList.get(0).getCode()+"");
                        }else{
                            tvAssets.setText(""+ equipmentList.get(0).getCode()+",...");
                            btnViewAllAssets.setVisibility(View.VISIBLE);
                        }

                        tvReturningUser.setText(""+returningUser);
                        tvCount.setText("");
                        tvCountSupportText.setText("Are you sure you want to " + tbTitleBottom.getText().toString() +" "+equipmentList.size() + " Asset(s)");
                        tvTaskName.setText("" + tbTitleBottom.getText().toString().toUpperCase());
                        isfinalCheckout = true;
                    } else {
                        showToast("yoo");
                    }
                    break;
                }
            }
        }

    };



    void showAssetList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, assetNames);


        LayoutInflater li = LayoutInflater.from(CheckoutAssetActivity.this);
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CheckoutAssetActivity.this);
        alertDialogBuilder.setTitle("Assets From " + department);
        alertDialogBuilder.setView(dialogView);
        final ListView listAssets = (ListView) dialogView
                .findViewById(R.id.lvAssets);
        listAssets.setAdapter(adapter);
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(CheckoutAssetActivity.this, BarcodeScanActivity.class);
            in.putExtra("taskType", taskType);
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(CheckoutAssetActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(CheckoutAssetActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutAssetActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(CheckoutAssetActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutAssetActivity.this);
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
                    ActivityCompat.requestPermissions(CheckoutAssetActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(CheckoutAssetActivity.this, BarcodeScanActivity.class);
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
                Intent in = new Intent(CheckoutAssetActivity.this, BarcodeScanActivity.class);
                in.putExtra("taskType", taskType);
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(CheckoutAssetActivity.this, Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutAssetActivity.this);
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(CheckoutAssetActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
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

        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(message);
        if (null != toolhawkEquipment) {
            if (dep.getCode().toLowerCase().equals(toolhawkEquipment.getDepartment().getCode().toLowerCase())) {
                if (!equipmentList.contains(toolhawkEquipment)) {
                    equipmentList.add(toolhawkEquipment);
                    rlBottomSheetMove.setVisibility(View.VISIBLE);
                    tvCount.setText(""+equipmentList.size());
                    mAdapter.notifyDataSetChanged();
                    etBarcode.setText("");
                    hideKeyboard();
                } else {
                    showToast("This Asset is already added");
                }
            } else {
                showToast("This Asset is not found in " + department);
            }
        } else {
            showToast("Asset not found!");
        }
    }
}


