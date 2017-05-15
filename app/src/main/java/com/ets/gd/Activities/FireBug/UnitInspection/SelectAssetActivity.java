package com.ets.gd.Activities.FireBug.UnitInspection;

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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.Scan.BarcodeScanActivity;
import com.ets.gd.Adapters.ScannedAssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.Location;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class SelectAssetActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany, ivTick;
    EditText etBarcode;
    ScannedAssetsAdapter mAdapter;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvUnderText, tvBarcodeTitle, tvBarcodeValue;
    Button btnScan, btnCross;
    RecyclerView rlLocs;
    public static List<FireBugEquipment> assetList = new ArrayList<FireBugEquipment>();
    Context mContext;
    String compName, repairSelection;

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
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBarcodeBroadcastReceiver,
                new IntentFilter("barcode-scanned"));
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


    private void setupLocList() {

        assetList.clear();
        assetList = DataManager.getInstance().getAllCompanyAssets(DataManager.getInstance().getCustomerByCode(compName).getID());
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
                sendMessage(assetList.get(position).getCode()+" "+repairSelection);
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
                    if ("".equals(etBarcode.getText().toString().trim())) {
                        Intent in = new Intent(SelectAssetActivity.this, BarcodeScanActivity.class);
                        in.putExtra("taskType", "loc");
                        startActivity(in);
                    } else {
                        FireBugEquipment fireBugEquipment = DataManager.getInstance().getEquipment(etBarcode.getText().toString().toString());
                        if (null != fireBugEquipment) {
//                            Intent in = new Intent(SelectAssetActivity.this,RepairAssetActivity.class);
//                            in.putExtra("compName",tvCompanyValue.getText().toString().trim());
//                            in.putExtra("tagID",fireBugEquipment.getCode());
//                            startActivity(in);
                            sendMessage(fireBugEquipment.getCode()+" "+repairSelection);
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


    private final BroadcastReceiver mBarcodeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            String task = intent.getStringExtra("taskType");
            if (task.startsWith("loc")) {

                FireBugEquipment fireBugEquipment = DataManager.getInstance().getEquipment(message);
                if (null != fireBugEquipment) {
                    sendMessage(fireBugEquipment.getCode()+" "+repairSelection);
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
    };

    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("moveToLoc");
        intent.putExtra("message", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
