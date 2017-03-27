package com.ets.gd.Activities.FireBug.Move;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ets.gd.Activities.FireBug.RouteInspection.RouteInspectionActivity;
import com.ets.gd.Activities.Scan.BarcodeScanActivity;
import com.ets.gd.Activities.Scan.CommonFirebugScanActivity;
import com.ets.gd.Adapters.ScannedAssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.Location;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class SelectLocationActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany, ivTick;
    EditText etBarcode;
    ScannedAssetsAdapter mAdapter;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvUnderText, tvBarcodeTitle, tvBarcodeValue;
    Button btnScan, btnCross;
    RecyclerView rlLocs;
    List<Location> locList = new ArrayList<Location>();
    Context mContext;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        initViews();
        initObj();
        initListeners();

    }


    private void initViews() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tvUnderText = (TextView) findViewById(R.id.tvUnderText);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        etBarcode = (EditText) findViewById(R.id.etBarcode);
        tvBarcodeTitle = (TextView) findViewById(R.id.tvBarcodeTitle);
        tvBarcodeValue = (TextView) findViewById(R.id.tvBarcodeValue);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        btnScan = (Button) findViewById(R.id.btnScan);
        rlLocs = (RecyclerView) findViewById(R.id.rlLocs);
        btnCross = (Button) findViewById(R.id.btnCross);
        ivChangeCompany.setVisibility(View.GONE);
        ivTick.setVisibility(View.GONE);
        tbTitleTop.setText("Firebug");
        tbTitleBottom.setText("Select Location");
        tvUnderText.setText("Enter Location ID");
    }

    private void initObj() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBarcodeBroadcastReceiver,
                new IntentFilter("barcode-scanned"));
        mContext = this;
        location = getIntent().getStringExtra("location");
        tvCompanyValue.setText(location);
        hideKeyboard();
        tvBarcodeTitle.setVisibility(View.GONE);
        tvBarcodeValue.setVisibility(View.GONE);
        btnCross.setVisibility(View.GONE);
        setupLocList();
        mAdapter = new ScannedAssetsAdapter(locList, getApplicationContext(), "loc");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rlLocs.setLayoutManager(mLayoutManager);
        rlLocs.setItemAnimator(new DefaultItemAnimator());
        rlLocs.setAdapter(mAdapter);
    }


    private void setupLocList() {

        locList.clear();
        locList = DataManager.getInstance().getAllLocations();
    }


    public void hideKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        btnScan.setOnClickListener(mGlobal_OnClickListener);

        rlLocs.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(SelectLocationActivity.this, rlLocs, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                sendMessage(locList.get(position).getLocationID());
                finish();
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

                case R.id.btnScan: {
                    Intent in  =new Intent(SelectLocationActivity.this, BarcodeScanActivity.class);
                    in.putExtra("taskType","loc");
                    startActivity(in);
                    break;
                }
            }
        }

    };

    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("moveToLoc");
        intent.putExtra("message", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    private final BroadcastReceiver mBarcodeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            String task = intent.getStringExtra("taskType");
            if (task.startsWith("loc")) {

                Location loc = new Location();
                loc.setLocationID("L000400");
                loc.setPlace("Shelf");
                loc.setDescription("Const");
                etBarcode.setText("");
                locList.add(loc);
                mAdapter.notifyDataSetChanged();
            }

        }
    };
}
