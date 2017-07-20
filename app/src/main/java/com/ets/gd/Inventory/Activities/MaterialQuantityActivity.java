package com.ets.gd.Inventory.Activities;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.MaterialAdded;
import com.ets.gd.Inventory.Adapters.MaterialAdapter;
import com.ets.gd.Models.Material;
import com.ets.gd.NetworkLayer.ResponseDTOs.Inventory;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class MaterialQuantityActivity extends AppCompatActivity {


    RecyclerView rvList;
    TextView tvMaterialFoundAt, tvNoLocFound;
    EditText etQuantity, etMaterialID;
    ImageView ivBack, ivTick;
    TextView tbTitleTop, tbTitleBottom;
    SharedPreferencesManager sharedPreferencesManager;
    String taskType, materialID;
    MaterialAdapter mAdapter;
    private List<Inventory> locList = new ArrayList<Inventory>();
    RelativeLayout rlBottomSheetJobnumber, rlYes, rlNo;
    TextView tvStatement;
    int materialLocID, eqID, inventoryID;
    public static MaterialAdded materialAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_quantity);
        initViews();
        initObj();
        initListeners();

        setupData();
    }


    private void initViews() {

        tvMaterialFoundAt = (TextView) findViewById(R.id.tvMaterialFoundAt);
        tvNoLocFound = (TextView) findViewById(R.id.tvNoLocFound);
        etMaterialID = (EditText) findViewById(R.id.etMaterialID);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvStatement = (TextView) findViewById(R.id.tvStatement);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        rlBottomSheetJobnumber = (RelativeLayout) findViewById(R.id.rlBottomSheetJobnumber);
        rlYes = (RelativeLayout) findViewById(R.id.rlYes);
        rlNo = (RelativeLayout) findViewById(R.id.rlNo);
        ivTick.setVisibility(View.GONE);
        taskType = getIntent().getStringExtra("taskType");
        materialID = getIntent().getStringExtra("materialID");
        tvStatement.setText("Do you want to assign a Job Number?");
        tvMaterialFoundAt.setText("Material " + materialID + " found at following Location(s).\n Please select a Location.");
        tbTitleTop.setText("Inventory");
        tbTitleBottom.setText("" + taskType);
        etMaterialID.setText("" + materialID);
        etMaterialID.clearFocus();
        etQuantity.requestFocus();

    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(MaterialQuantityActivity.this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
    }

    private void initListeners() {

        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlYes.setOnClickListener(mGlobal_OnClickListener);
        rlNo.setOnClickListener(mGlobal_OnClickListener);

        rvList.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(MaterialQuantityActivity.this, rvList, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                hideKeyboard();

                if (checkValidation()) {

                    int alreadyAddedQuantity = 0;
                    if (null != MoveMaterialScanListActivity.materialList) {
                        for (Material mat : MoveMaterialScanListActivity.materialList) {
                            if (etMaterialID.getText().toString().toLowerCase().equals(mat.getName().toLowerCase())
                                    && locList.get(position).getLocationID() == mat.getLocID()) {
                                alreadyAddedQuantity = Integer.parseInt(mat.getQuantity());
                                break;
                            }
                        }
                    }

                    int totalQuantity = alreadyAddedQuantity + Integer.parseInt(etQuantity.getText().toString());

                    if (locList.get(position).getQuantity() < totalQuantity) {

                        if (0 == alreadyAddedQuantity) {
                            showToast("This location doesn't contain " + etQuantity.getText().toString() + " Material(s)!");
                        } else {
                            showToast("You have already selected " + alreadyAddedQuantity + " Material(s)!");
                            showToast("This location doesn't contain " + totalQuantity + " Material(s)!");
                        }
                    } else {
                        etQuantity.setEnabled(false);
                        boolean isLoc;
                        if (0!=locList.get(position).getLocationID()) {
                            materialLocID = locList.get(position).getLocationID();
                            isLoc = true;
                        } else {
                            materialLocID = locList.get(position).getEquipmentID();
                            isLoc = false;
                        }
                        inventoryID = locList.get(position).getID();
                        eqID = locList.get(position).getEquipmentID();
                        if (!MoveMaterialScanListActivity.addMoreMaretailItem) {
                            Intent in = new Intent(MaterialQuantityActivity.this, MoveMaterialScanListActivity.class);
                            in.putExtra("materialID", materialID);
                            in.putExtra("taskType", taskType);
                            in.putExtra("isLoc", isLoc);
                            in.putExtra("inventoryID", inventoryID);
                            in.putExtra("materialLocID", materialLocID);
                            in.putExtra("quantity", etQuantity.getText().toString());
                            startActivity(in);
                            etQuantity.setText("");
                            etQuantity.setEnabled(true);
                        } else {
                            MoveMaterialScanListActivity.addMoreMaretailItem = false;
                            materialAdded.MaterialMoveListItemAdded(new Material(eqID, materialID, etQuantity.getText().toString(), materialLocID, inventoryID,isLoc));
                            sendMessage("finish");
                        }

                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (taskType.toLowerCase().startsWith("rec")) {
                    if (s.length() >= 1) {
                        showReceiveBottomSheet();
                    } else {
                        hideBottomsheet();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean checkValidation() {
        if ("".equals(etQuantity.getText().toString().trim())) {
            showToast("Please enter Quantity first");
        } else if ("0".equals(etQuantity.getText().toString().trim())) {
            showToast("Please enter Quantity greater than 0");
        } else {
            return true;
        }
        return false;
    }

    private void hideBottomsheet() {

        rlBottomSheetJobnumber.setVisibility(View.GONE);
    }

    private void showReceiveBottomSheet() {
        rlBottomSheetJobnumber.setVisibility(View.VISIBLE);
        tvStatement.setText("Do you want to " + taskType + " " + etMaterialID.getText().toString() + ", Quantity " + etQuantity.getText().toString() + " ?");
    }


    private void showReceiveBottomSheetJobNumber() {
        rlBottomSheetJobnumber.setVisibility(View.VISIBLE);
        tvStatement.setText("Do you want to assign a Job Number?");
    }


    private void setupData() {


        if (!taskType.toLowerCase().startsWith("rec")) {
            hideKeyboard();
            locList = DataManager.getInstance().getInventoryListByMaterialID(DataManager.getInstance().getMaterial(materialID).getID());

            if (0 == locList.size()) {
                tvNoLocFound.setVisibility(View.VISIBLE);
            }

            mAdapter = new MaterialAdapter(MaterialQuantityActivity.this, locList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaterialQuantityActivity.this);
            rvList.setLayoutManager(mLayoutManager);
            rvList.setItemAnimator(new DefaultItemAnimator());
            rvList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            tvMaterialFoundAt.setText("Please enter " + materialID + " Quantity you want to Receive");
            showKeyboard();
        }
    }

    private void showKeyboard() {

        etQuantity.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etQuantity,
                InputMethodManager.SHOW_IMPLICIT);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {

                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.rlYes: {
                    if (!MoveMaterialScanListActivity.addMoreMaretailItem) {
                        Intent in = new Intent(MaterialQuantityActivity.this, MoveMaterialScanListActivity.class);
                        in.putExtra("materialID", materialID);
                        in.putExtra("taskType", taskType);
                        in.putExtra("inventoryID", inventoryID);
                        in.putExtra("quantity", etQuantity.getText().toString());
                        startActivity(in);
                        etQuantity.setText("");
                        etQuantity.setEnabled(true);
                    } else {
                        MoveMaterialScanListActivity.addMoreMaretailItem = false;
                        materialAdded.MaterialMoveListItemAdded(new Material(eqID, materialID, etQuantity.getText().toString(), materialLocID, inventoryID,false));
                        sendMessage("finish");
                    }
                    break;
                }


                case R.id.rlNo: {
                    finish();
                    sendMessage("finish");

                }
                break;
            }
        }

    };


    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("move-complete");
        intent.putExtra("message", msg);
        intent.putExtra("type", "fin");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void hideKeyboard() {
        etQuantity.clearFocus();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                etQuantity.getWindowToken(), 0);

    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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

}
