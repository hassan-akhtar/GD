package com.ets.gd.Activities.FireBug.ViewInformation;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;

public class ViewLocationInformationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack,ivTick;
    Spinner spSite, spBuilding;
    private EditText tvLocationID, tvDescprition;
    private TextInputLayout lLocationID, lDescprition;
    public static String actionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location_information);

        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {

        spSite = (Spinner) findViewById(R.id.spSite);
        spBuilding = (Spinner) findViewById(R.id.spBuilding);
        lLocationID = (TextInputLayout) findViewById(R.id.lLocationID);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        lDescprition = (TextInputLayout) findViewById(R.id.lDescprition);
        tvLocationID = (EditText) findViewById(R.id.tvLocationID);
        tvDescprition = (EditText) findViewById(R.id.tvDescprition);
        ivBack = (ImageView) findViewById(R.id.ivBack);


    }

    private void initObj() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vendors));
        dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterVendor);

        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.agents));
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBuilding.setAdapter(dataAdapterAgent);

        actionType = getIntent().getStringExtra("action");
        if("viewLoc".equals(actionType)){
            ivTick.setVisibility(View.GONE);
            setViewForViewLoc();
        }else {
            ivTick.setVisibility(View.VISIBLE);
            setViewForAddLoc();
        }

    }


    void setViewForViewLoc() {

        tvLocationID.setText("L00382");
        tvDescprition.setText("Prod");

        spSite.setSelection(1);
        spBuilding.setSelection(1);

    }


    void setViewForAddLoc() {

        tvLocationID.setText("");
        tvDescprition.setText("");

        spSite.setSelection(0);
        spBuilding.setSelection(0);


    }

    private void initListeners() {
        spSite.setOnItemSelectedListener(this);
        spBuilding.setOnItemSelectedListener(this);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivTick: {
                    Toast.makeText(getApplicationContext(),"Add Location",Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }

    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spDeviceType: {

                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;

            case R.id.spManufacturer: {
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;


            case R.id.spVendor: {
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;


            case R.id.spAgent: {
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
