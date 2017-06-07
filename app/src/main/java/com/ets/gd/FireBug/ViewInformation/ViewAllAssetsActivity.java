package com.ets.gd.FireBug.ViewInformation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ets.gd.Adapters.ScannedAssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Scan.CommonFirebugScanActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAllAssetsActivity extends AppCompatActivity {


    ImageView ivBack, ivTick, ivChangeCompany;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue;
    RecyclerView rlAssets;
    private List<FireBugEquipment> assetList = new ArrayList<FireBugEquipment>();
    ScannedAssetsAdapter mAdapter;
    String locCode, compName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_assets);

        initViews();
        initObj();
        initListeners();


    }


    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        rlAssets = (RecyclerView) findViewById(R.id.lvAssets);
        ivTick.setVisibility(View.GONE);

        locCode = getIntent().getStringExtra("locCode");
        compName = getIntent().getStringExtra("compName");
        ivChangeCompany.setVisibility(View.GONE);
        tvCompanyValue.setText("" + compName);
    }

    private void initObj() {

        assetList = DataManager.getInstance().getFirebugLocEquipments(locCode);
        mAdapter = new ScannedAssetsAdapter(getApplicationContext(), assetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rlAssets.setLayoutManager(mLayoutManager);
        rlAssets.setItemAnimator(new DefaultItemAnimator());
        rlAssets.setAdapter(mAdapter);
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlAssets.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getApplicationContext(), rlAssets, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Intent in = new Intent(ViewAllAssetsActivity.this, ViewAssetInformationActivity.class);
                in.putExtra("action", "viewAsset");
                in.putExtra("compName", tvCompanyValue.getText().toString());
                in.putExtra("barCode", assetList.get(position).getCode());
                startActivity(in);
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
            }
        }

    };

}
