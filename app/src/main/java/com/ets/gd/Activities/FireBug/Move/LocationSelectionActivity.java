package com.ets.gd.Activities.FireBug.Move;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.Customer.CustomerActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.AssetList;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentList;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class LocationSelectionActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany, ivTick;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvFromLoc, tvMovingAsset, tvAssetsNames, tvToLoc, tvStatement;
    Button btnSelectLoc, btnViewAllAssets;
    String taskName, companyName, loc;
    public static List<FireBugEquipment> asset = new ArrayList<FireBugEquipment>();
    String[] assetNames;
    RelativeLayout rlYes, rlNo, rlBottomSheet;
    private String taskType;
    int count;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        initViews();
        initObj();
        initListeners();
        setupData();

    }


    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        tvStatement = (TextView) findViewById(R.id.tvStatement);
        tvFromLoc = (TextView) findViewById(R.id.tvFromLoc);
        tvMovingAsset = (TextView) findViewById(R.id.tvMovingAsset);
        tvAssetsNames = (TextView) findViewById(R.id.tvAssetsNames);
        tvToLoc = (TextView) findViewById(R.id.tvToLoc);
        btnSelectLoc = (Button) findViewById(R.id.btnSelectLoc);
        btnViewAllAssets = (Button) findViewById(R.id.btnViewAllAssets);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        rlYes = (RelativeLayout) findViewById(R.id.rlYes);
        rlNo = (RelativeLayout) findViewById(R.id.rlNo);
        rlBottomSheet = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);
        ivTick.setVisibility(View.GONE);
        ivChangeCompany.setVisibility(View.GONE);
    }

    private void initObj() {

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mToLocBroadcastReceiver,
                new IntentFilter("moveToLoc"));
        sharedPreferencesManager = new SharedPreferencesManager(LocationSelectionActivity.this);
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivChangeCompany.setOnClickListener(mGlobal_OnClickListener);
        btnSelectLoc.setOnClickListener(mGlobal_OnClickListener);
        btnViewAllAssets.setOnClickListener(mGlobal_OnClickListener);
        rlYes.setOnClickListener(mGlobal_OnClickListener);
        rlNo.setOnClickListener(mGlobal_OnClickListener);
    }


    private void setupData() {

        taskName = getIntent().getStringExtra("taskType");
        count = getIntent().getIntExtra("count", 0);
        companyName = getIntent().getStringExtra("compName");

        loc = getIntent().getStringExtra("loc");
        tbTitleBottom.setText(taskName);
        tvCompanyValue.setText(companyName);
        tvFromLoc.setText(loc);

        tvMovingAsset.setText("Moving " + asset.size() + " asset(s)");

        if (1 < asset.size()) {
            btnViewAllAssets.setVisibility(View.VISIBLE);
            tvAssetsNames.setText(asset.get(0).getManufacturers().getCode()+" ,"+asset.get(0).getModel().getCode());
        } else {
            btnViewAllAssets.setVisibility(View.GONE);
            tvAssetsNames.setText(asset.get(0).getManufacturers().getCode()+" ,"+asset.get(0).getModel().getCode());
        }

        assetNames = new String[asset.size()];

        for (int i = 0; i < asset.size(); i++) {
            assetNames[i] = asset.get(i).getManufacturers().getCode() + ", " + asset.get(i).getModel().getCode();
        }


    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivChangeCompany: {
                    finish();
                    break;
                }

                case R.id.rlYes: {

                    if (taskName.toLowerCase().startsWith("m")) {
                        DataManager.getInstance().updateAssetLocationID(asset,tvToLoc.getText().toString(),"move",0 );
                        Toast.makeText(getApplicationContext(),"Asset(s) Successfully Moved!",Toast.LENGTH_LONG).show();
                    } else if (taskName.toLowerCase().startsWith("t")) {
                        DataManager.getInstance().updateAssetLocationID(asset,tvToLoc.getText().toString(),"transfer",sharedPreferencesManager.getInt(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_ID));
                        Toast.makeText(getApplicationContext(),"Asset(s) Successfully Transferred!",Toast.LENGTH_LONG).show();
                    }
                    sendMessage("finish");
                    finish();
                    break;
                }

                case R.id.rlNo: {
                    finish();
                    break;
                }

                case R.id.btnSelectLoc: {
                    if (taskName.toLowerCase().startsWith("m")) {
                        startActivity(new Intent(LocationSelectionActivity.this, SelectLocationActivity.class));
                    } else if (taskName.toLowerCase().startsWith("t")) {
                        startActivity(new Intent(LocationSelectionActivity.this, CustomerActivity.class));
                    }
                    break;
                }


                case R.id.btnViewAllAssets: {
                    showAssetList();
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

    void showAssetList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, assetNames);


        LayoutInflater li = LayoutInflater.from(LocationSelectionActivity.this);
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LocationSelectionActivity.this);
        alertDialogBuilder.setTitle("Assets From " + loc);
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

    private final BroadcastReceiver mToLocBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            tvToLoc.setText(message);
            btnSelectLoc.setText("Change Location");
            if (taskName.toLowerCase().startsWith("m")) {
                tvStatement.setText("Are you sure you want to move " + asset.size() + " Asset(s) To " + message);
                rlBottomSheet.setVisibility(View.VISIBLE);
            } else if (taskName.toLowerCase().startsWith("t")) {
                tvStatement.setText("Are you sure you want to transfer " + asset.size() + " Asset(s) To " + message + " in "+sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME));
                rlBottomSheet.setVisibility(View.VISIBLE);
            }

        }
    };


}
