package com.ets.gd.Activities.FireBug.UnitInspection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.R;

public class RepairAssetActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany, ivTick;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvTagID, tvDsiRepair, tvGDRepair;
    String compName, tagId, repairSelection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_asset);
        initViews();
        initObj();
        initListeners();

    }


    private void initViews() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        tvDsiRepair = (TextView) findViewById(R.id.tvDsiRepair);
        tvGDRepair = (TextView) findViewById(R.id.tvGDRepair);
        tvTagID = (TextView) findViewById(R.id.tvTagID);
        ivChangeCompany.setVisibility(View.GONE);
        ivTick.setVisibility(View.GONE);
    }

    private void initObj() {
        tbTitleBottom.setText("Replace Asset");
        compName = getIntent().getStringExtra("compName");
        tagId = getIntent().getStringExtra("tagID");
        tvCompanyValue.setText("" + compName);
        tvTagID.setText("" + tagId);
    }

    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("moveToLoc");
        intent.putExtra("message", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvGDRepair.setOnClickListener(mGlobal_OnClickListener);
        tvDsiRepair.setOnClickListener(mGlobal_OnClickListener);

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    RepairAssetActivity.this.finish();
                    break;
                }

                case R.id.tvGDRepair: {
                    repairSelection = "from GD repairs";
                    sendMessage(tagId+" "+repairSelection);
                    finish();
                    break;
                }


                case R.id.tvDsiRepair: {
                    repairSelection = "from DSI repairs";
                    sendMessage(tagId+" "+repairSelection);
                    finish();
                    break;
                }

            }
        }

    };
}
