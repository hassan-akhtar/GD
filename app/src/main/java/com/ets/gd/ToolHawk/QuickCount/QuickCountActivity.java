package com.ets.gd.ToolHawk.QuickCount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Models.Department;
import com.ets.gd.Models.ToolhawkAsset;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.CommonToolhawkDepartmentActivity;
import com.ets.gd.ToolHawk.Adapters.DepartmentAdapter;
import com.ets.gd.ToolHawk.Adapters.QuickCountAdapter;
import com.ets.gd.ToolHawk.Move.MoveActivity;

import java.util.ArrayList;
import java.util.List;

public class QuickCountActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom, tvAssetOtherInfo, tvLocName, tvBarcodeTitle, tvUnderText, tvBarcodeValue;
    TextView tvUnExpected, tvExpected, tvFound;
    String taskType, locationCode;
    Button btnCross, btnScan;
    LinearLayout llbtns;
    EditText etBarcode;
    ImageView ivInfo;
    ImageView ivBack, ivTick;
    RecyclerView rvQuickCount;
    QuickCountAdapter mAdapter;
    private List<ToolhawkEquipment> assetList = new ArrayList<ToolhawkEquipment>();
    ETSLocations etsLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_count);

        initViews();
        initObj();
        initListeners();
        setupView();
        hideKeyboard();
        setupAssetList();

    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvAssetOtherInfo = (TextView) findViewById(R.id.tvAssetOtherInfo);
        tvUnExpected = (TextView) findViewById(R.id.tvUnExpected);
        tvExpected = (TextView) findViewById(R.id.tvExpected);
        tvFound = (TextView) findViewById(R.id.tvFound);
        tvLocName = (TextView) findViewById(R.id.tvLocName);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        btnCross = (Button) findViewById(R.id.btnCross);
        rvQuickCount = (RecyclerView) findViewById(R.id.rvQuickCount);
        btnScan = (Button) findViewById(R.id.btnScan);
        llbtns = (LinearLayout) findViewById(R.id.llbtns);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);

        taskType = getIntent().getStringExtra("taskType");
        locationCode = getIntent().getStringExtra("locationCode");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("Quick Count");

        etsLocation = DataManager.getInstance().getETSLocationsByCode(locationCode);

        if (null != etsLocation) {
            tvLocName.setText("" + etsLocation.getCode());
            tvAssetOtherInfo.setText("" + etsLocation.getDescription());
        }

    }

    private void initObj() {
    }


    private void initListeners() {
        btnScan.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);

        rvQuickCount.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(QuickCountActivity.this, rvQuickCount, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showToast("" + assetList.get(position).getCode());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        ivInfo.setVisibility(View.VISIBLE);
        tvUnderText.setVisibility(View.VISIBLE);
        llbtns.setVisibility(View.GONE);
        tvBarcodeValue.setText("");
        etBarcode.setVisibility(View.VISIBLE);
        etBarcode.setText("");
        btnCross.setVisibility(View.GONE);
    }


    private void setupAssetList() {
/*        ToolhawkAsset asset = new ToolhawkAsset();
        asset.setName("Pick Up");
        asset.setCode("112233");
        asset.setLoc("Yard");
        asset.setParent(true);
        assetList.add(asset);
        asset = new ToolhawkAsset();
        asset.setName("Step Van");
        asset.setCode("334455");
        asset.setLoc("Annex");
        asset.setParent(false);
        assetList.add(asset);
        asset = new ToolhawkAsset();
        asset.setName("Pick Up");
        asset.setCode("112233");
        asset.setLoc("Yard");
        asset.setParent(true);
        assetList.add(asset);*/

        assetList = DataManager.getInstance().getAllToolhawkEquipmentForLocation(locationCode);
        tvExpected.setText("" + assetList.size());
        mAdapter = new QuickCountAdapter(QuickCountActivity.this, assetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(QuickCountActivity.this);
        rvQuickCount.setLayoutManager(mLayoutManager);
        rvQuickCount.setItemAnimator(new DefaultItemAnimator());
        rvQuickCount.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.btnScan: {
                    showToast("Scan");
                    break;
                }
            }
        }

    };

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        return false;
    }
}

