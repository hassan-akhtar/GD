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

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Location;
import com.ets.gd.R;

public class ViewLocationInformationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack, ivTick;
    Spinner spSite, spBuilding;
    private EditText tvLocationID, tvDescprition;
    Location location;
    private TextInputLayout lLocationID, lDescprition;
    public static String actionType, barCodeID;
    int posSite = 0 , posBuilding = 0;

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
        location = DataManager.getInstance().getLocation(barCodeID);

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
        if ("viewLoc".equals(actionType)) {
            ivTick.setVisibility(View.GONE);
            setViewForViewLoc();
        } else {
            ivTick.setVisibility(View.VISIBLE);
            setViewForAddLoc();
        }

    }


    void setViewForViewLoc() {

        tvLocationID.setText(location.getLocationID());
        tvDescprition.setText(location.getDescription());

        for (int i = 0; i < getResources().getStringArray(R.array.vendors).length; i++) {
            if (location.getVendor().toLowerCase().equals(spSite.getItemAtPosition(i).toString().toLowerCase())) {
                spSite.setSelection(i);
            }
        }

        for (int i = 0; i < getResources().getStringArray(R.array.agents).length; i++) {
            if (location.getAgent().toString().toLowerCase().equals(spBuilding.getItemAtPosition(i).toString().toLowerCase())) {
                spBuilding.setSelection(i);
            }
        }

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
                    if (checkValidation()) {
                        if (!DataManager.getInstance().doesLocationExist(tvLocationID.getText().toString().trim())) {
                            DataManager.getInstance().addLocation(

                                    new Location(tvLocationID.getText().toString().trim(),
                                            tvDescprition.getText().toString().trim(),spSite.getItemAtPosition(posSite).toString(),
                                            spBuilding.getItemAtPosition(posBuilding).toString(),"Shelf")
                            );
                            Toast.makeText(getApplicationContext(), "Location Added!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Location Already Added!", Toast.LENGTH_LONG).show();
                        }
                    }

                    break;
                }
            }
        }

    };

    private boolean checkValidation() {
        if ("".equals(tvLocationID.getText().toString().trim())) {
            showToast("Please enter location ID");
        } else if ("".equals(tvDescprition.getText().toString().trim())) {
            showToast("Please enter Description");
        } else if (0==posSite) {
            showToast("Please select a site");
        } else if (0==posBuilding) {
            showToast("Please select a building");
        } else {
            return true;
        }

        return false;
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spSite: {
                posSite = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;

            case R.id.spBuilding: {
                posBuilding = position;
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
