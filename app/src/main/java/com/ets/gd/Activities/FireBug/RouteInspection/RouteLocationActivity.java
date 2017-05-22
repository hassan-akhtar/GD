package com.ets.gd.Activities.FireBug.RouteInspection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Adapters.RouteInspLocAdapter;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.Routes;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class RouteLocationActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom, tvCompanyName, tvRouteType, tvDesc, tvRouteName, tvLocCount;
    ImageView ivBack, ivTick;
    String compName;
    public static Routes route;
    RecyclerView rvRouteInspection;
    RouteInspLocAdapter routeInspLocAdapter;
    List<RouteLocation> locationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_insp_location);
        initViews();
        initObj();
        initListeners();
    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvRouteType = (TextView) findViewById(R.id.tvRouteType);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvRouteName = (TextView) findViewById(R.id.tvRouteName);
        tvLocCount = (TextView) findViewById(R.id.tvLocCount);
        tvCompanyName = (TextView) findViewById(R.id.tvCompanyValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        rvRouteInspection = (RecyclerView) findViewById(R.id.rvRouteInspection);
        tbTitleBottom.setText("Route Inspection");
        compName = getIntent().getStringExtra("compName");
        locationList = route.getRouteLocations();
        tvRouteName.setText(route.getCode());
        tvDesc.setText(route.getDescription());
        tvRouteType.setText(route.getRouteType());

        tvLocCount.setText(""+route.getRouteLocations().size());
        routeInspLocAdapter = new RouteInspLocAdapter(RouteLocationActivity.this,locationList);
        tvCompanyName.setText(compName);
        ivTick.setVisibility(View.GONE);
    }

    private void initObj() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RouteLocationActivity.this);
        rvRouteInspection.setLayoutManager(mLayoutManager);
        rvRouteInspection.setItemAnimator(new DefaultItemAnimator());
        rvRouteInspection.setAdapter(routeInspLocAdapter);
        routeInspLocAdapter.notifyDataSetChanged();
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);

        rvRouteInspection.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(RouteLocationActivity.this, rvRouteInspection, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (0!=locationList.get(position).getRouteAssets().size()) {
                    RouteAssetActivity.routeLocation = locationList.get(position);
                    Intent in = new Intent(RouteLocationActivity.this, RouteAssetActivity.class);
                    in.putExtra("compName", tvCompanyName.getText().toString());
                    in.putExtra("locCount", ""+locationList.size());
                    in.putExtra("routeName", tvRouteName.getText().toString());
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(),"No Route Location Assets(s) Found.",Toast.LENGTH_LONG).show();
                }
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