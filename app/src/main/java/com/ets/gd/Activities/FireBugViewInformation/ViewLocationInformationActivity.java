package com.ets.gd.Activities.FireBugViewInformation;

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

import com.ets.gd.R;

public class ViewLocationInformationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack;
    Spinner spSite, spBuilding;
    private EditText tvLocationID, tvDescprition;
    private TextInputLayout lLocationID, lDescprition;
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
        lDescprition = (TextInputLayout) findViewById(R.id.lDescprition);
        tvLocationID = (EditText) findViewById(R.id.tvLocationID);
        tvDescprition = (EditText) findViewById(R.id.tvDescprition);
        ivBack = (ImageView) findViewById(R.id.ivBack);


        tvLocationID.setText("L00382");
        tvDescprition.setText("Prod");
    }

    private void initObj() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vendors));
        dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterVendor);

        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.agents));
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBuilding.setAdapter(dataAdapterAgent);

        spSite.setSelection(1);
        spBuilding.setSelection(1);
    }

    private void initListeners() {
        spSite.setOnItemSelectedListener(this);
        spBuilding.setOnItemSelectedListener(this);
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


            case R.id.spMfgDate: {
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
