package com.ets.gd.Inventory.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.MaterialAdded;
import com.ets.gd.Inventory.Adapters.MaterialAdapter;
import com.ets.gd.Models.Material;
import com.ets.gd.Models.Note;
import com.ets.gd.NetworkLayer.ResponseDTOs.Department;
import com.ets.gd.NetworkLayer.ResponseDTOs.Inventory;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.CommonToolhawkDepartmentActivity;
import com.ets.gd.ToolHawk.Activities.CommonToolhawkScanActivity;
import com.ets.gd.ToolHawk.Adapters.DepartmentAdapter;
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
    int materialLocID ;
    public static MaterialAdded materialAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_quantity);
        initViews();
        initObj();
        initListeners();
        hideKeyboard();
        setupLocList();
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
                if (!"".equals(etQuantity.getText().toString().trim())) {
                    if (locList.get(position).getQuantity()<Integer.parseInt(etQuantity.getText().toString())) {
                        showToast("This location doesn't contain "+etQuantity.getText().toString()+" Material(s)!");
                    } else {
                        materialLocID = locList.get(position).getLocationID();
                        rlBottomSheetJobnumber.setVisibility(View.VISIBLE);

                    }
                } else {
                    showToast("Please enter quantity first!");
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    private void setupLocList() {


        locList = DataManager.getInstance().getInventoryListByMaterialID(DataManager.getInstance().getMaterial(materialID).getID());

        if(0==locList.size()){
            tvNoLocFound.setVisibility(View.VISIBLE);
        }

        mAdapter = new MaterialAdapter(MaterialQuantityActivity.this, locList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaterialQuantityActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {

                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.rlYes: {
                    Intent in = new Intent(MaterialQuantityActivity.this, InventoryJobNumberActivity.class);
                    in.putExtra("materialLocID", materialLocID);
                    in.putExtra("materialID", materialID);
                    in.putExtra("taskType", taskType);
                    in.putExtra("quantity", etQuantity.getText().toString());
                    startActivity(in);
                    etQuantity.setText("");
                    break;
                }


                case R.id.rlNo: {
                    if (!MoveMaterialScanListActivity.addMoreMaretailItem) {
                        Intent in = new Intent(MaterialQuantityActivity.this, MoveMaterialScanListActivity.class);
                        in.putExtra("materialLocID", materialLocID);
                        in.putExtra("materialID", materialID);
                        in.putExtra("taskType", taskType);
                        in.putExtra("quantity", etQuantity.getText().toString());
                        startActivity(in);
                        etQuantity.setText("");
                    } else {
                        materialAdded.MaterialMoveListItemAdded(new Material(materialID, etQuantity.getText().toString(),materialLocID));
                        sendMessage("finish");

                    }
                    break;
                }
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