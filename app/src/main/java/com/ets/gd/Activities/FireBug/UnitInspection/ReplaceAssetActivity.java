package com.ets.gd.Activities.FireBug.UnitInspection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Interfaces.AssetReplaced;
import com.ets.gd.Models.Replace;
import com.ets.gd.R;

public class ReplaceAssetActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany, ivTick;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvFromLoc, tvToLoc, tvStatement;
    Button btnSelectLoc, btnViewAllAssets;
    String taskName, companyName;
    RelativeLayout rlYes, rlNo, rlBottomSheet;
    private String taskType, code;
    public static String replaceType;
    public static  int newLocID=0, newEquipID=0;
    public static AssetReplaced assetReplaced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_asset);
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
        code = getIntent().getStringExtra("code");
        companyName = getIntent().getStringExtra("compName");
        tbTitleBottom.setText(taskName);
        tvCompanyValue.setText(companyName);
        tvFromLoc.setText(code);
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
                    Toast.makeText(getApplicationContext(), "Asset Successfully Replaced!", Toast.LENGTH_LONG).show();
                    //sendMessage("replace",replaceType,newLocID , newEquipID );
                    assetReplaced.AssetReplaced(new Replace("replace",replaceType,newLocID , newEquipID ));
                    finish();
                    break;
                }

                case R.id.rlNo: {
                    finish();
                    break;
                }

                case R.id.btnSelectLoc: {
                    Intent in = new Intent(ReplaceAssetActivity.this, RepairAssetActivity.class);
                    in.putExtra("tagCode", code);
                    in.putExtra("compName", companyName);
                    startActivity(in);
                    break;
                }

            }
        }

    };

    private void sendMessage(String msg, String replaceType, int newLocationID, int newEquipmentID) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("replace-complete");
        intent.putExtra("message", msg);
        intent.putExtra("replaceType", replaceType);
        intent.putExtra("newLocID", newLocID);
        intent.putExtra("newEquipmentID", newEquipmentID);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    private final BroadcastReceiver mToLocBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            tvToLoc.setText(message);
            btnSelectLoc.setText("Change Asset");
            tvStatement.setText("Are you sure you want to REPLACE asset " + code + " with asset " + message);
            rlBottomSheet.setVisibility(View.VISIBLE);


        }
    };


}
