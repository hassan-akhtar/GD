package com.ets.gd.Activities.FireBug.RouteInspection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.R;

public class RouteInspectionActivity extends AppCompatActivity {

    TextView tbTitleTop, tbTitleBottom, tvRouteCount, tvCompanyName;
    ImageView ivBack, ivTick;
    String compName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_inspection);

        initViews();
        initObj();
        initListeners();

    }


    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvRouteCount = (TextView) findViewById(R.id.tvRouteCount);
        tvCompanyName = (TextView) findViewById(R.id.tvCompanyName);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tbTitleBottom.setText("Route Inspection");

        compName = getIntent().getStringExtra("compName");
        tvCompanyName.setText(compName);
/*        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        vLine = (View) findViewById(R.id.vLine);
        btnLoc = (Button) findViewById(R.id.btnLoc);
        btnAsset = (Button) findViewById(R.id.btnAsset);*/

        ivTick.setVisibility(View.GONE);
    }

    private void initObj() {
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
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
