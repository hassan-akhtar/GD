package com.ets.gd.Activities.FireBug.RouteInspection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.Adapters.RouteInspLocAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteAsset;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.Routes;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class RouteInspAssetActivity extends AppCompatActivity {

    public static RouteLocation routeLocation;
    TextView tbTitleTop, tbTitleBottom, tvCompanyName, tvAssetCount, tvLocCount, tvRouteName, tvLocName;
    ImageView ivBack, ivTick;
    String compName, locCount, routeName;
    RecyclerView rvRouteInspection;
    List<RouteAsset> assetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_insp_asset);
        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvAssetCount = (TextView) findViewById(R.id.tvAssetCount);
        tvLocCount = (TextView) findViewById(R.id.tvLocCount);
        tvRouteName = (TextView) findViewById(R.id.tvRouteName);
        tvLocName = (TextView) findViewById(R.id.tvLocName);
        tvCompanyName = (TextView) findViewById(R.id.tvCompanyValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        rvRouteInspection = (RecyclerView) findViewById(R.id.rvRouteInspection);
        tbTitleBottom.setText("Route Inspection");
        compName = getIntent().getStringExtra("compName");
        locCount = getIntent().getStringExtra("locCount");
        routeName = getIntent().getStringExtra("routeName");
        tvCompanyName.setText(compName);

        tvAssetCount.setText("" + routeLocation.getRouteAssets().size());
        tvLocCount.setText("" + locCount);
        tvRouteName.setText("" + routeName);
        tvLocName.setText("" + DataManager.getInstance().getLocationByID(routeLocation.getLocationID()).getCode());

        ivTick.setVisibility(View.GONE);

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
