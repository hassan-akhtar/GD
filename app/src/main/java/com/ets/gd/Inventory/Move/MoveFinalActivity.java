package com.ets.gd.Inventory.Move;

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

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Customer.CustomerActivity;
import com.ets.gd.FireBug.Move.SelectLocationActivity;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class MoveFinalActivity extends AppCompatActivity {


    ImageView ivBack, ivTick;
    TextView tbTitleTop, tbTitleBottom, tvFromLoc, tvMovingAsset, tvAssetsNames, tvToLoc, tvStatement;
    Button btnSelectLoc, btnViewAllAssets, btnViewAllLocations;
    String taskName, loc,toLoc , jobNumber;
    public static List<FireBugEquipment> asset = new ArrayList<FireBugEquipment>();
    String[] assetNames;
    public static String[] locationNames;
    RelativeLayout rlYes, rlNo, rlBottomSheet, rlAssetInfo;
    private String taskType;
    int count;
    int locID, cusID;
    TextView tvJobNumber;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_move_final);

        initViews();
        initObj();
        initListeners();
        setupData();

    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvStatement = (TextView) findViewById(R.id.tvStatement);
        tvFromLoc = (TextView) findViewById(R.id.tvFromLoc);
        tvMovingAsset = (TextView) findViewById(R.id.tvMovingAsset);
        tvAssetsNames = (TextView) findViewById(R.id.tvAssetsNames);
        tvJobNumber = (TextView) findViewById(R.id.tvJobNumber);
        tvToLoc = (TextView) findViewById(R.id.tvToLoc);
        btnSelectLoc = (Button) findViewById(R.id.btnSelectLoc);
        btnViewAllLocations = (Button) findViewById(R.id.btnViewAllLocations);
        btnViewAllAssets = (Button) findViewById(R.id.btnViewAllAssets);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        rlYes = (RelativeLayout) findViewById(R.id.rlYes);
        rlNo = (RelativeLayout) findViewById(R.id.rlNo);
        rlAssetInfo  = (RelativeLayout) findViewById(R.id.rlAssetInfo);
        rlBottomSheet = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);
        ivTick.setVisibility(View.GONE);
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(MoveFinalActivity.this);
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        btnSelectLoc.setOnClickListener(mGlobal_OnClickListener);
        btnViewAllAssets.setOnClickListener(mGlobal_OnClickListener);
        btnViewAllLocations.setOnClickListener(mGlobal_OnClickListener);
        rlYes.setOnClickListener(mGlobal_OnClickListener);
        rlNo.setOnClickListener(mGlobal_OnClickListener);
    }


    private void setupData() {

        taskName = getIntent().getStringExtra("taskType");
        count = getIntent().getIntExtra("count", 0);
        jobNumber = getIntent().getStringExtra("jobNumber");
        loc = getIntent().getStringExtra("loc");
        toLoc = getIntent().getStringExtra("toLoc");
        tbTitleBottom.setText(taskName);

        tvToLoc.setText(""+toLoc);

        if(null!=jobNumber){
            rlAssetInfo.setVisibility(View.VISIBLE);
            tvJobNumber.setText(""+jobNumber);
        }else{
            rlAssetInfo.setVisibility(View.GONE);
        }

        if (taskName.toLowerCase().startsWith("m")) {
            btnSelectLoc.setVisibility(View.VISIBLE);
            btnSelectLoc.setText("Change Location");
        } else if (taskName.toLowerCase().startsWith("i")) {

        } else {
            btnSelectLoc.setText("Select Location");
        }




//        if (1 < locationNames.length) {
//            btnViewAllLocations.setVisibility(View.VISIBLE);
//            if (loc.length()<17) {
//                tvFromLoc.setText(loc + ",...");
//            }else{
//                tvFromLoc.setText(loc.substring(0,15)+",...");
//            }
//
//        } else {
            btnViewAllLocations.setVisibility(View.GONE);
            tvFromLoc.setText(loc);
       // }

        tvMovingAsset.setText("Moving " + asset.size() + " Material(s)");

        if (1 < asset.size()) {
            btnViewAllAssets.setVisibility(View.VISIBLE);
            //tvAssetsNames.setText(asset.get(0).getManufacturers().getCode() + ",...");
        } else {
            btnViewAllAssets.setVisibility(View.GONE);
            //tvAssetsNames.setText(asset.get(0).getManufacturers().getCode());
            tvAssetsNames.setText("xxxxxxx");
        }

        assetNames = new String[asset.size()];

        for (int i = 0; i < asset.size(); i++) {
            assetNames[i] = asset.get(i).getManufacturers().getCode() + ", " + asset.get(i).getModel().getCode();
        }

        if (taskName.toLowerCase().startsWith("m")) {
            tvStatement.setText("Are you sure you want to move " + asset.size() + " Material(s) To " + toLoc);
            rlBottomSheet.setVisibility(View.VISIBLE);
        } else if (taskName.toLowerCase().startsWith("i")) {
            tvStatement.setText("Are you sure you want to issue " + asset.size() + " Material(s) To " + toLoc);
            rlBottomSheet.setVisibility(View.VISIBLE);
        }
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.rlYes: {

                    if (taskName.toLowerCase().startsWith("m")) {
                        Toast.makeText(getApplicationContext(), "Asset(s) Successfully Moved!", Toast.LENGTH_LONG).show();
                    } else if (taskName.toLowerCase().startsWith("i")) {
                        Toast.makeText(getApplicationContext(), "Asset(s) Successfully Issued!", Toast.LENGTH_LONG).show();
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
                    finish();
                    break;
                }


                case R.id.btnViewAllAssets: {
                    showAssetList();
                    break;
                }

                case R.id.btnViewAllLocations: {
                    showLocationsList();
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


        LayoutInflater li = LayoutInflater.from(MoveFinalActivity.this);
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MoveFinalActivity.this);
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

    void showLocationsList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, locationNames);


        LayoutInflater li = LayoutInflater.from(MoveFinalActivity.this);
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MoveFinalActivity.this);
        alertDialogBuilder.setTitle("Locations ");
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

}

