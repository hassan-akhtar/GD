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
import com.ets.gd.NetworkLayer.RequestDTOs.InventoryMove;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveInventory;
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
    String[] assetNames;
    public static String[] locationNames;
    RelativeLayout rlYes, rlNo, rlBottomSheet, rlAssetInfo;
    private String taskType,scanType;
    int count;
    int locID, cusID;
    TextView tvJobNumber;
    SharedPreferencesManager sharedPreferencesManager;
    public static List<Material> materialList = new ArrayList<Material>();
    MoveInventory moveInventory;
    private List<InventoryMove> Materials;
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
        scanType  = getIntent().getStringExtra("scanType");
        count = materialList.size();
        jobNumber = getIntent().getStringExtra("jobNumber");
        loc = DataManager.getInstance().getETSLocationByIDOnly(materialList.get(0).getLocID()).getCode();
        toLoc = getIntent().getStringExtra("toLoc");
        tbTitleBottom.setText(taskName);

        tvToLoc.setText(""+toLoc);

        if(null!=jobNumber){
            rlAssetInfo.setVisibility(View.VISIBLE);
            tvJobNumber.setText(""+jobNumber);
        }else{
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
            if (loc.length()<17) {
                tvFromLoc.setText(loc + ",...");
            }else{
                tvFromLoc.setText(loc.substring(0,15)+",...");
            }

        } else {
            btnViewAllLocations.setVisibility(View.GONE);
            tvFromLoc.setText(loc);
        }

        tvMovingAsset.setText("Moving " + materialList.size() + " Material(s)");

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
            tvStatement.setText("Are you sure you want to move " + materialList.size() + " Material(s) To " + toLoc);
            rlBottomSheet.setVisibility(View.VISIBLE);
        } else if (taskName.toLowerCase().startsWith("i")) {
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

                    if (taskName.toLowerCase().startsWith("m")) {
                        if (scanType.startsWith("con")) {
                            moveInventory.setEquipmentID(DataManager.getInstance().getToolhawkEquipment(tvToLoc.getText().toString()).getID());
                            moveInventory.setMoveType("Container");
                        }
                        if (scanType.startsWith("loc")) {
                            moveInventory.setLocationID(DataManager.getInstance().getETSLocations(tvToLoc.getText().toString()).getID());
                            moveInventory.setMoveType("Location");
                        }
                        moveInventory.setUserID(0);

                        moveInventory.setJobNumberID(materialList.get(0).getJobNumberID());
                       for(int i=0;i<materialList.size();i++){
                           InventoryMove inventoryMove = new InventoryMove();
                           inventoryMove.setCode(materialList.get(i).getName());
                           inventoryMove.setInventoryID(DataManager.getInstance().getInventoryByMaterialID(DataManager.getInstance().getMaterial(materialList.get(i).getName()).getID()).getID());
                           inventoryMove.setMaterialID(DataManager.getInstance().getMaterial(materialList.get(i).getName()).getID());
                           inventoryMove.setQuantity(Integer.parseInt(materialList.get(i).getQuantity()));

                           Materials.add(inventoryMove);
                       }
                        moveInventory.setMaterials(Materials);
                        DataManager.getInstance().saveMoveInventoryResult(moveInventory);
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
        alertDialogBuilder.setTitle("Materials" );
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
