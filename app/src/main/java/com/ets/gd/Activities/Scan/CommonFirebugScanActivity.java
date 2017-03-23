package com.ets.gd.Activities.Scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
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

import com.ets.gd.Activities.FireBug.Move.LocationSelectionActivity;
import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Activities.FireBug.ViewInformation.ViewLocationInformationActivity;
import com.ets.gd.Adapters.ScannedAssetsAdapter;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.AssetList;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class CommonFirebugScanActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany, ivTick;
    EditText etBarcode;
    ScannedAssetsAdapter mAdapter;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvUnderText, tvBarcodeTitle, tvBarcodeValue;
    TextView tvCount, tvCountSupportText, tvTaskName;
    RelativeLayout rlForwardArrow, rlBottomSheet;
    Button btnLoc, btnAsset, btnScan, btnCross;
    View vLine;
    RecyclerView rlAssets;
    Asset asset;
    String taskType, compName;
    LinearLayout llbtns, llunderText;
    private List<Asset> assetList = new ArrayList<Asset>();
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
        ivTick = (ImageView) findViewById(R.id.ivTick);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCountSupportText = (TextView) findViewById(R.id.tvCountSupportText);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        rlForwardArrow = (RelativeLayout) findViewById(R.id.rlForwardArrow);
        rlBottomSheet = (RelativeLayout) findViewById(R.id.rlBottomSheet);
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
        mAdapter = new ScannedAssetsAdapter(getApplicationContext(), assetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rlAssets.setLayoutManager(mLayoutManager);
        rlAssets.setItemAnimator(new DefaultItemAnimator());
        rlAssets.setAdapter(mAdapter);
        asset = new Asset();
    }

    private void initListeners() {
        btnLoc.setOnClickListener(mGlobal_OnClickListener);
        btnAsset.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        btnCross.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);
        ivChangeCompany.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnScan: {
                    startActivity(new Intent(CommonFirebugScanActivity.this, BarcodeScanActivity.class));
                    break;
                }

                case R.id.btnAsset: {
                    Intent in = new Intent(CommonFirebugScanActivity.this, ViewAssetInformationActivity.class);
                    in.putExtra("action", "viewAsset");
                    startActivity(in);
                    hideScannedData();

                    break;
                }

                case R.id.btnLoc: {
                    Intent in = new Intent(CommonFirebugScanActivity.this, ViewLocationInformationActivity.class);
                    in.putExtra("action", "viewLoc");
                    startActivity(in);
                    hideScannedData();
                    break;
                }

                case R.id.ivBack: {
                    finish();
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
                    in.putExtra("taskType",taskType);
                    in.putExtra("compName",compName);
                    in.putExtra("assetList",listAssets);
                    in.putExtra("loc",assetList.get(0).getLocation());
                    startActivity(in);
                    break;
                }
            }
        }

    };


    public void hideKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private final BroadcastReceiver mBarcodeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            if (taskType.toLowerCase().startsWith("v")) {
                tvBarcodeValue.setText(message);
                etBarcode.setVisibility(View.INVISIBLE);
                tvBarcodeTitle.setVisibility(View.VISIBLE);
                tvBarcodeValue.setVisibility(View.VISIBLE);
                btnCross.setVisibility(View.VISIBLE);
                llunderText.setVisibility(View.GONE);
                llbtns.setVisibility(View.VISIBLE);
            } else if (taskType.toLowerCase().startsWith("m")) {
                asset.setName("Ansul");
                asset.setCode("An350");
                asset.setTag("00382");
                asset.setLocation("L00416");
                etBarcode.setText("");
                rlBottomSheet.setVisibility(View.VISIBLE);
                assetList.add(asset);
                tvCount.setText(""+assetList.size());
                mAdapter.notifyDataSetChanged();

            } else {
                showToast(taskType + ": " + message);
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
