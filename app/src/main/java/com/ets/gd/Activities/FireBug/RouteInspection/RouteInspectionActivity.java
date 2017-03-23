package com.ets.gd.Activities.FireBug.RouteInspection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ets.gd.R;

public class RouteInspectionActivity extends AppCompatActivity {

    TextView tbTitleTop, tbTitleBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_inspection);

        initViews();
        initObj();
        initListeners();

    }


    private void initViews() {
/*        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);*/
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tbTitleBottom.setText("Route Inspection");
/*        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        vLine = (View) findViewById(R.id.vLine);
        btnLoc = (Button) findViewById(R.id.btnLoc);
        btnAsset = (Button) findViewById(R.id.btnAsset);*/
    }
    private void initObj() {
    }

    private void initListeners() {
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnLogin: {

                    break;
                }
            }
        }

    };
}
