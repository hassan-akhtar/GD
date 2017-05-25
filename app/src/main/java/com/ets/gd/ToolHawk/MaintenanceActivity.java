package com.ets.gd.ToolHawk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.R;

public class MaintenanceActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom, tvAssetID;
    String assetID;
    ImageView ivBack, ivTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        initViews();
        initObj();
        initListeners();
        setupView();

    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvAssetID = (TextView) findViewById(R.id.tvAssetID);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);

        assetID = getIntent().getStringExtra("assetID");
        tvAssetID.setText(""+assetID);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("Maintenance");

    }

    private void initObj() {
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
    }

    private void setupView() {
            ivTick.setVisibility(View.GONE);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivTick: {

                    break;
                }
            }
        }

    };

    private boolean checkValidation() {
        return false;
    }
}
