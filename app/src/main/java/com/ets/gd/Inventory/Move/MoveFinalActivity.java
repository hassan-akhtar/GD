package com.ets.gd.Inventory.Move;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.ets.gd.Models.Material;
import com.ets.gd.NetworkLayer.RequestDTOs.InventoryMoveRealm;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveInventoryRealm;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class MoveFinalActivity extends AppCompatActivity {


    ImageView ivBack, ivTick;
    TextView tbTitleTop, tbTitleBottom, tvFromLoc, tvMovingAsset, tvAssetsNames, tvToLoc, tvStatement;
    Button btnSelectLoc, btnViewAllAssets, btnViewAllLocations;
    String taskName, loc, toLoc, jobNumber;
    String[] assetNames;
    public static String[] locationNames;
    RelativeLayout rlYes, rlNo, rlBottomSheet, rlAssetInfo;
    private String taskType, scanType;
    int count;
    int locID, cusID;
    TextView tvJobNumber;
    SharedPreferencesManager sharedPreferencesManager;
    public static List<Material> materialList = new ArrayList<Material>();
    MoveInventoryRealm moveInventory;
    private RealmList<InventoryMoveRealm> Materials = new RealmList<InventoryMoveRealm>();
    int JobNumberID;

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
        rlAssetInfo = (RelativeLayout) findViewById(R.id.rlAssetInfo);
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
        scanType = getIntent().getStringExtra("scanType");
        JobNumberID = getIntent().getIntExtra("JobNumberID",0);
        count = materialList.size();
        jobNumber = getIntent().getStringExtra("jobNumber");
        if (null != DataManager.getInstance().getETSLocationByIDOnly(materialList.get(0).getLocID())) {
            loc = DataManager.getInstance().getETSLocationByIDOnly(materialList.get(0).getLocID()).getCode();
        } else if (null != DataManager.getInstance().getToolhawkEquipmentByID(materialList.get(0).getEquipmentID())) {
            loc = DataManager.getInstance().getToolhawkEquipmentByID(materialList.get(0).getEquipmentID()).getCode();
        } else {
            loc = "N/A";
        }
        toLoc = getIntent().getStringExtra("toLoc");
        tbTitleBottom.setText(taskName);

        tvToLoc.setText("" + toLoc);

        if (null != jobNumber) {
            rlAssetInfo.setVisibility(View.VISIBLE);
            tvJobNumber.setText("" + jobNumber);
        } else {
            rlAssetInfo.setVisibility(View.GONE);
        }

        rlAssetInfo.setVisibility(View.GONE);

        if (taskName.toLowerCase().startsWith("m")) {
            btnSelectLoc.setVisibility(View.VISIBLE);
            btnSelectLoc.setText("Change Location");
        } else if (taskName.toLowerCase().startsWith("i")) {

        } else {
            btnSelectLoc.setText("Select Location");
        }


        if (1 < locationNames.length) {
            btnViewAllLocations.setVisibility(View.VISIBLE);
            if (loc.length() < 17) {
                tvFromLoc.setText(loc + ",...");
            } else {
                tvFromLoc.setText(loc.substring(0, 15) + ",...");
            }

        } else {
            btnViewAllLocations.setVisibility(View.GONE);
            tvFromLoc.setText(loc);
        }


        if (1 < materialList.size()) {
            btnViewAllAssets.setVisibility(View.VISIBLE);
            tvAssetsNames.setText(materialList.get(0).getName() + ",...");
        } else {
            btnViewAllAssets.setVisibility(View.GONE);
            tvAssetsNames.setText(materialList.get(0).getName());
        }

        assetNames = new String[materialList.size()];

        for (int i = 0; i < materialList.size(); i++) {
            assetNames[i] = materialList.get(i).getName();
        }

        if (taskName.toLowerCase().startsWith("m")) {
            tvMovingAsset.setText("Moving " + materialList.size() + " Material(s)");
            tvStatement.setText("Are you sure you want to move " + materialList.size() + " Material(s) To " + toLoc);
            rlBottomSheet.setVisibility(View.VISIBLE);
        } else if (taskName.toLowerCase().startsWith("is")) {
            tvMovingAsset.setText("Issuing " + materialList.size() + " Material(s)");
            tvStatement.setText("Are you sure you want to issue " + materialList.size() + " Material(s) To " + toLoc);
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

                    if (!tvToLoc.getText().toString().toLowerCase().equals(tvFromLoc.getText().toString())) {
                        moveInventory = new MoveInventoryRealm();
                        if (taskName.toLowerCase().startsWith("m")) {
                            moveInventory.setMoved(true);
                            if (scanType.toLowerCase().startsWith("con")) {
                                moveInventory.setEquipmentID(DataManager.getInstance().getToolhawkEquipment(tvToLoc.getText().toString()).getID());
                                moveInventory.setMoveType("Equipment");
                            }
                            if (scanType.toLowerCase().startsWith("loc")) {
                                moveInventory.setLocationID(DataManager.getInstance().getETSLocations(tvToLoc.getText().toString()).getID());
                                moveInventory.setMoveType("Location");
                            }
                            moveInventory.setUserID( sharedPreferencesManager.getInt(SharedPreferencesManager.LOGGED_IN_USERID));

                            moveInventory.setJobNumberID(JobNumberID);
                            for (int i = 0; i < materialList.size(); i++) {
                                InventoryMoveRealm inventoryMove = new InventoryMoveRealm();
                                inventoryMove.setCode(materialList.get(i).getName());
                                inventoryMove.setInventoryID(materialList.get(i).getInventoryID());
                                inventoryMove.setMaterialID(DataManager.getInstance().getMaterial(materialList.get(i).getName()).getID());
                                inventoryMove.setQuantity(Integer.parseInt(materialList.get(i).getQuantity()));
                                Materials.add(inventoryMove);
                                DataManager.getInstance().updateInventoryQuantity(DataManager.getInstance().getMaterial(materialList.get(i).getName()).getID(),materialList.get(i).getLocID(),Integer.parseInt(materialList.get(i).getQuantity()));
                            }
                            moveInventory.setMaterials(Materials);
                            DataManager.getInstance().saveMoveInventoryResult(moveInventory);

                            Toast.makeText(getApplicationContext(), "Material(s) Successfully Moved!", Toast.LENGTH_LONG).show();
                        } else if (taskName.toLowerCase().startsWith("iss")) {
                            moveInventory.setIssued(true);
                            if (scanType.toLowerCase().startsWith("con")) {
                                moveInventory.setEquipmentID(DataManager.getInstance().getToolhawkEquipment(tvToLoc.getText().toString()).getID());
                                moveInventory.setIssueType("Equipment");
                            }
                            if (scanType.toLowerCase().startsWith("use")) {
                                moveInventory.setUserID(DataManager.getInstance().getMobileUser(tvToLoc.getText().toString()).getID());
                                moveInventory.setIssueType("User");
                            }

                            moveInventory.setJobNumberID(JobNumberID);

                            for (int i = 0; i < materialList.size(); i++) {
                                InventoryMoveRealm inventoryMove = new InventoryMoveRealm();
                                inventoryMove.setCode(materialList.get(i).getName());
                                inventoryMove.setInventoryID(materialList.get(i).getInventoryID());
                                inventoryMove.setMaterialID(DataManager.getInstance().getMaterial(materialList.get(i).getName()).getID());
                                inventoryMove.setQuantity(Integer.parseInt(materialList.get(i).getQuantity()));
                                Materials.add(inventoryMove);
                                DataManager.getInstance().updateInventoryQuantity(DataManager.getInstance().getMaterial(materialList.get(i).getName()).getID(),materialList.get(i).getLocID(),Integer.parseInt(materialList.get(i).getQuantity()));
                            }
                            moveInventory.setMaterials(Materials);

                            DataManager.getInstance().saveMoveInventoryResult(moveInventory);
                            Toast.makeText(getApplicationContext(), "Material(s) Successfully Issued!", Toast.LENGTH_LONG).show();
                        }
                        sendMessage("finish");
                        finish();
                    } else {
                        Toast.makeText(MoveFinalActivity.this, "You cannot " + taskName + " to same Location/Container.", Toast.LENGTH_SHORT).show();
                        if (taskName.toLowerCase().startsWith("m")) {
                            Toast.makeText(MoveFinalActivity.this, "Please select any other Location/Container.", Toast.LENGTH_LONG).show();
                        } else if (taskName.toLowerCase().startsWith("iss")) {
                            Toast.makeText(MoveFinalActivity.this, "Please select any other User/Container.", Toast.LENGTH_LONG).show();
                        }
                    }
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
        alertDialogBuilder.setTitle("Materials");
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

