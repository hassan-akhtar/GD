package com.ets.gd.FireBug.RouteInspection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Adapters.RouteInspectionAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Interfaces.RouteCompleted;
import com.ets.gd.NetworkLayer.ResponseDTOs.Routes;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class RouteInspectionActivity extends AppCompatActivity implements RouteCompleted {

    TextView tbTitleTop, tbTitleBottom, tvRouteCount, tvCompanyName;
    ImageView ivBack, ivTick;
    String compName, taskType;
    List<Routes> routesList = new ArrayList<Routes>();
    RouteInspectionAdapter routeInspectionAdapter;
    RecyclerView rvRouteInspection;

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
        rvRouteInspection = (RecyclerView) findViewById(R.id.rvRouteInspection);
        taskType = getIntent().getStringExtra("taskType");
        compName = getIntent().getStringExtra("compName");
        tvCompanyName.setText(compName);
        tbTitleBottom.setText(taskType);
        ivTick.setVisibility(View.GONE);
    }

    private void initObj() {

        routesList = DataManager.getInstance().getAllInspectionRoutes(DataManager.getInstance().getCustomerByCode(tvCompanyName.getText().toString()).getID());
        tvRouteCount.setText("" + routesList.size());
        routeInspectionAdapter = new RouteInspectionAdapter(routesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RouteInspectionActivity.this);
        rvRouteInspection.setLayoutManager(mLayoutManager);
        rvRouteInspection.setItemAnimator(new DefaultItemAnimator());
        rvRouteInspection.setAdapter(routeInspectionAdapter);
        routeInspectionAdapter.notifyDataSetChanged();
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);

        rvRouteInspection.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(RouteInspectionActivity.this, rvRouteInspection, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (0 != routesList.get(position).getRouteLocations().size()) {
                    setRouteCompleteInterface();
                    RouteLocationActivity.route = null;
                    RouteLocationActivity.route = routesList.get(position);
                    Intent in = new Intent(RouteInspectionActivity.this, RouteLocationActivity.class);
                    in.putExtra("compName", tvCompanyName.getText().toString());
                    in.putExtra("cusID", routesList.get(position).getCustomerID());
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), "No Route Location(s) Found.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setRouteCompleteInterface() {
        RouteLocationActivity.routeCompleted = this;
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

    @Override
    public void routeCompleted(String routeName) {
        routesList.clear();
        routesList = DataManager.getInstance().getAllInspectionRoutes(DataManager.getInstance().getCustomerByCode(tvCompanyName.getText().toString()).getID());
        tvRouteCount.setText("" + routesList.size());
        routeInspectionAdapter = new RouteInspectionAdapter(routesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RouteInspectionActivity.this);
        rvRouteInspection.setLayoutManager(mLayoutManager);
        rvRouteInspection.setItemAnimator(new DefaultItemAnimator());
        rvRouteInspection.setAdapter(routeInspectionAdapter);
        routeInspectionAdapter.notifyDataSetChanged();
        showToast("Route " + routeName + " marked as Completed!");
    }

    void showToast(String msg) {
        Toast.makeText(RouteInspectionActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}
