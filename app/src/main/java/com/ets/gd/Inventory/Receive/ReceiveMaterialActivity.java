package com.ets.gd.Inventory.Receive;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.ets.gd.ToolHawk.CheckInOut.CheckoutAssetActivity;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class ReceiveMaterialActivity extends AppCompatActivity {


    TextView tvTextandCount, tvMaterial, tvLoc;
    TextView tbTitleTop, tbTitleBottom, tvCount, tvTaskName, tvCountSupportText;
    RelativeLayout rlBottomSheetMove, rlForwardArrow;
    Button btnViewAllMaterials;
    ImageView ivBack, ivTick;
    SharedPreferencesManager sharedPreferencesManager;
    Context mContext;
    String[] materialNames;
    String loc;
    MoveInventoryRealm moveInventory;
    public static List<Material> materialList = new ArrayList<Material>();
    private RealmList<InventoryMoveRealm> Materials = new RealmList<InventoryMoveRealm>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_material);

        initViews();
        initObj();
        initListeners();
        setupView();
    }

    private void initViews() {
        btnViewAllMaterials = (Button) findViewById(R.id.btnViewAllMaterials);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvTextandCount = (TextView) findViewById(R.id.tvTextandCount);
        tvMaterial = (TextView) findViewById(R.id.tvMaterial);
        tvLoc = (TextView) findViewById(R.id.tvLoc);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvTaskName = (TextView) findViewById(R.id.tvTaskName);
        tvCountSupportText = (TextView) findViewById(R.id.tvCountSupportText);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        rlBottomSheetMove = (RelativeLayout) findViewById(R.id.rlBottomSheetMove);
        rlForwardArrow = (RelativeLayout) findViewById(R.id.rlForwardArrow);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(ReceiveMaterialActivity.this);
        mContext = this;
    }

    private void initListeners() {
        btnViewAllMaterials.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlForwardArrow.setOnClickListener(mGlobal_OnClickListener);
    }


    private void setupView() {
        ivTick.setVisibility(View.GONE);
        tbTitleTop.setText("Inventory");
        tbTitleBottom.setText("Receive");
        loc = getIntent().getStringExtra("toLoc");
        tvLoc.setText("" + loc);

        if (1 == materialList.size()) {
            tvMaterial.setText("" + materialList.get(0).getName());
        } else if (1 < materialList.size()) {
            tvMaterial.setText("" + materialList.get(0).getName() + ",...");
            btnViewAllMaterials.setVisibility(View.VISIBLE);
        }

        tvTextandCount.setText("Receiving " + materialList.size() + " Material(s)");

        tvCount.setText(""+materialList.size());
        tvTaskName.setText("RECEIVE");
        tvCountSupportText.setText("Material(s) to be Received");

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {

                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.rlForwardArrow: {
                    moveInventory = new MoveInventoryRealm();
                    moveInventory.setReceived(true);
                    moveInventory.setLocationID(DataManager.getInstance().getETSLocations(tvLoc.getText().toString()).getID());
                    moveInventory.setUserID(0);
                    moveInventory.setJobNumberID(materialList.get(0).getJobNumberID());

                    for (int i = 0; i < materialList.size(); i++) {
                        InventoryMoveRealm inventoryMove = new InventoryMoveRealm();
                        inventoryMove.setCode(materialList.get(i).getName());
                        inventoryMove.setInventoryID(materialList.get(i).getInventoryID());
                        inventoryMove.setMaterialID(DataManager.getInstance().getMaterial(materialList.get(i).getName()).getID());
                        inventoryMove.setQuantity(Integer.parseInt(materialList.get(i).getQuantity()));
                        Materials.add(inventoryMove);
                    }
                    moveInventory.setMaterials(Materials);
                    DataManager.getInstance().saveMoveInventoryResult(moveInventory);
                    Toast.makeText(getApplicationContext(), "Material(s) Successfully Received!", Toast.LENGTH_LONG).show();
                    sendMessage("finish");
                    finish();
                    break;
                }



                case R.id.btnViewAllMaterials: {

                    materialNames = new String[materialList.size()];

                    for (int i = 0; i < materialList.size(); i++) {
                        materialNames[i] = materialList.get(i).getName();
                    }
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
                android.R.layout.simple_list_item_1, android.R.id.text1, materialNames);


        LayoutInflater li = LayoutInflater.from(ReceiveMaterialActivity.this);
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ReceiveMaterialActivity.this);
        alertDialogBuilder.setTitle("Materials ");
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


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}
