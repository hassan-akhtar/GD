package com.ets.gd.FireBug.Move;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class LocationSelectionActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany, ivTick;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvFromLoc, tvMovingAsset, tvAssetsNames, tvToLoc, tvStatement;
    Button btnSelectLoc, btnViewAllAssets, btnViewAllLocations, btnSelectCompany, btnSelectLoction;
    String taskName, companyName, loc;
    public static List<FireBugEquipment> asset = new ArrayList<FireBugEquipment>();
    String[] assetNames;
    public static String[] locationNames;
    RelativeLayout rlYes, rlNo, rlBottomSheet, rlTransferOptions;
    private String taskType, newLocCode;
    int count;
    int locID, cusID;
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
        btnSelectCompany = (Button) findViewById(R.id.btnSelectCompany);
        btnSelectLoction = (Button) findViewById(R.id.btnSelectLoction);
        btnViewAllLocations = (Button) findViewById(R.id.btnViewAllLocations);
        btnViewAllAssets = (Button) findViewById(R.id.btnViewAllAssets);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        rlYes = (RelativeLayout) findViewById(R.id.rlYes);
        rlNo = (RelativeLayout) findViewById(R.id.rlNo);
        rlBottomSheet = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);
        rlTransferOptions = (RelativeLayout) findViewById(R.id.rlTransferOptions);
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

        btnSelectCompany.setOnClickListener(mGlobal_OnClickListener);
        btnSelectLoction.setOnClickListener(mGlobal_OnClickListener);

        btnViewAllAssets.setOnClickListener(mGlobal_OnClickListener);
        btnViewAllLocations.setOnClickListener(mGlobal_OnClickListener);
        rlYes.setOnClickListener(mGlobal_OnClickListener);
        rlNo.setOnClickListener(mGlobal_OnClickListener);
    }


    private void setupData() {

        taskName = getIntent().getStringExtra("taskType");
        count = getIntent().getIntExtra("count", 0);
        companyName = getIntent().getStringExtra("compName");


        if (taskName.toLowerCase().startsWith("m")) {
            btnSelectLoc.setVisibility(View.VISIBLE);
            rlTransferOptions.setVisibility(View.GONE);
            btnSelectLoc.setText("Select Location");
        } else if (taskName.toLowerCase().startsWith("t")) {
            btnSelectLoc.setVisibility(View.VISIBLE);
            rlTransferOptions.setVisibility(View.GONE);
            btnSelectLoc.setText("Select Company");
        } else {
            btnSelectLoc.setText("Select Location");
        }


        loc = getIntent().getStringExtra("loc");
        tbTitleBottom.setText(taskName);
        tvCompanyValue.setText(companyName);

        if (1 < locationNames.length) {
            btnViewAllLocations.setVisibility(View.VISIBLE);
            if (loc.length() < 17) {
                tvFromLoc.setText(loc + " in " + tvCompanyValue.getText().toString() + ",...");
            } else {
                tvFromLoc.setText(loc.substring(0, 15) + "..." + " in " + tvCompanyValue.getText().toString() + ",...");
            }

        } else {
            btnViewAllLocations.setVisibility(View.GONE);
            tvFromLoc.setText(loc + " in " + tvCompanyValue.getText().toString());
        }

        tvMovingAsset.setText("Moving " + asset.size() + " asset(s)");

        if (1 < asset.size()) {
            btnViewAllAssets.setVisibility(View.VISIBLE);
            tvAssetsNames.setText(asset.get(0).getCode() + ",...");
        } else {
            btnViewAllAssets.setVisibility(View.GONE);
            tvAssetsNames.setText(asset.get(0).getCode());
        }

        assetNames = new String[asset.size()];

        for (int i = 0; i < asset.size(); i++) {
            assetNames[i] = asset.get(i).getCode();
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
                        DataManager.getInstance().updateAssetLocationID(asset, String.valueOf(locID), newLocCode, "move", 0);
                        Toast.makeText(getApplicationContext(), "Asset(s) Successfully Moved!", Toast.LENGTH_LONG).show();
                    } else if (taskName.toLowerCase().startsWith("t")) {
                        DataManager.getInstance().updateAssetLocationID(asset, String.valueOf(locID), newLocCode, "transfer",
                                DataManager.getInstance().getCustomerByCode(sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME)).getID());
                        Toast.makeText(getApplicationContext(), "Asset(s) Successfully Transferred!", Toast.LENGTH_LONG).show();
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
                        Intent in = new Intent(LocationSelectionActivity.this, SelectLocationActivity.class);
                        in.putExtra("compName", tvCompanyValue.getText().toString());
                        in.putExtra("Loc", loc);
                        in.putExtra("type", "move");
                        startActivity(in);
                    } else if (taskName.toLowerCase().startsWith("t")) {
                        Intent in = new Intent(LocationSelectionActivity.this, CustomerActivity.class);
                        in.putExtra("type", "transfer");
                        in.putExtra("compName", tvCompanyValue.getText().toString());
                        startActivity(in);
                    }
                    break;
                }

                case R.id.btnSelectCompany: {
                    Intent in = new Intent(LocationSelectionActivity.this, CustomerActivity.class);
                    in.putExtra("compName", tvCompanyValue.getText().toString());
                    startActivity(in);
                    break;
                }


                case R.id.btnSelectLoction: {
                    Intent in = new Intent(LocationSelectionActivity.this, SelectLocationActivity.class);
                    in.putExtra("compName", sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME));
                    in.putExtra("type", "transfer");
                    startActivity(in);
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

    void showLocationsList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, locationNames);


        LayoutInflater li = LayoutInflater.from(LocationSelectionActivity.this);
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LocationSelectionActivity.this);
        alertDialogBuilder.setTitle("Locations From " + tvCompanyValue.getText().toString());
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
            newLocCode = message;
            locID = intent.getIntExtra("locID", 0);
            cusID = intent.getIntExtra("cusID", 0);
            tvToLoc.setText(message);
            if (taskName.toLowerCase().startsWith("m")) {
                btnSelectLoc.setText("Change Location");
            } else if (taskName.toLowerCase().startsWith("t")) {
                btnSelectLoc.setVisibility(View.GONE);
                rlTransferOptions.setVisibility(View.VISIBLE);
                btnSelectCompany.setText("Change Company");
                btnSelectLoction.setText("Change Location");
            } else {
                btnSelectLoc.setText("Change Location");
            }
            if (taskName.toLowerCase().startsWith("m")) {
                tvStatement.setText("Are you sure you want to move " + asset.size() + " Asset(s) To " + message);
                rlBottomSheet.setVisibility(View.VISIBLE);
            } else if (taskName.toLowerCase().startsWith("t")) {
                tvStatement.setText("Are you sure you want to transfer " + asset.size() + " Asset(s) To " + message + " in " + sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME));
                rlBottomSheet.setVisibility(View.VISIBLE);
            }

        }
    };


}
