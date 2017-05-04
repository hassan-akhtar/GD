package com.ets.gd.Fragments;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Activities.FireBug.ViewInformation.ViewLocationInformationActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import io.realm.RealmList;


public class AssetLocationFragment extends Fragment implements Spinner.OnItemSelectedListener {


    public static Spinner spLocation, spSite, spBuilding, spCustomer;
    View rootView;
    public static EditText tvDescprition,tvLocationID;;
    private TextInputLayout letLocationID, lLocationID, lDescprition;
    // Asset asset;
    SharedPreferencesManager sharedPreferencesManager;
    public static int posLoc = 0, posSite = 0, posBuilding = 0, posCustomer = 0;
    FireBugEquipment fireBugEquipment;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;
    String[] sites;
    String[] buildings;
    String[] locations;
    String[] customers;
    Customer customer;

    public AssetLocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_asset_location, container, false);


        initViews();
        initObj();
        initListeners();
        return rootView;

    }

    private void initViews() {
        spCustomer = (Spinner) rootView.findViewById(R.id.spCustomer);
        spSite = (Spinner) rootView.findViewById(R.id.spSite);
        spBuilding = (Spinner) rootView.findViewById(R.id.spBuilding);
        spLocation = (Spinner) rootView.findViewById(R.id.spLocation);
        lLocationID = (TextInputLayout) rootView.findViewById(R.id.lLocationID);
        letLocationID  = (TextInputLayout) rootView.findViewById(R.id.letLocationID);
        tvDescprition = (EditText) rootView.findViewById(R.id.tvDescprition);
        tvLocationID = (EditText) rootView.findViewById(R.id.tvLocationID);
        letLocationID.setVisibility(View.INVISIBLE);
        tvLocationID.setVisibility(View.INVISIBLE);

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            spCustomer.setBackgroundColor(Color.parseColor("#ffffff"));
            spSite.setBackgroundColor(Color.parseColor("#ffffff"));
            spLocation.setBackgroundColor(Color.parseColor("#ffffff"));
            spBuilding.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        //asset = ((ViewAssetInformationActivity) getActivity()).getAsset();
        fireBugEquipment = ((ViewAssetInformationActivity) getActivity()).getEquipment();

        if (!"addAsset".equals(getActivity().getIntent().getStringExtra("action"))) {
            customer = fireBugEquipment.getCustomer();
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        }else{
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        }


        RealmList<Locations> locationsRealmList = new RealmList<Locations>();

        for (int k = 0 ; k <realmSyncGetResponseDTO.getLstLocations().size();k++)
        {
            if(!realmSyncGetResponseDTO.getLstLocations().get(k).isAdded()){
                locationsRealmList.add(realmSyncGetResponseDTO.getLstLocations().get(k));
            }
        }

        int size = locationsRealmList.size()+ 1 ;
         locations = new String[size];

        for (int i = 0; i < locationsRealmList.size(); i++) {
            locations[i+1] = locationsRealmList.get(i).getCode();
        }
        locations[0] = "Please select a location";
        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, locations);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(dataAdapterAgent);



        int sizeCustomer = realmSyncGetResponseDTO.getLstCustomers().size() + 1;
        customers = new String[sizeCustomer];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstCustomers().size(); i++) {
            customers[i + 1] = realmSyncGetResponseDTO.getLstCustomers().get(i).getCode();
        }
        customers[0] = "Please select a company";
        ArrayAdapter<String> dataAdapterCustomer = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, customers);
        dataAdapterCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomer.setAdapter(dataAdapterCustomer);



        int sizeSite = realmSyncGetResponseDTO.getLstLocations().size() + 1;
        sites = new String[sizeSite];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            sites[i + 1] = realmSyncGetResponseDTO.getLstLocations().get(i).getSite().getCode();
        }
        sites[0] = "Please select a site";
        ArrayAdapter<String> dataAdapterSIte = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sites);
        dataAdapterSIte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterSIte);


        int sizeBuilding = realmSyncGetResponseDTO.getLstLocations().size() + 1;
        buildings = new String[sizeBuilding];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            buildings[i + 1] = realmSyncGetResponseDTO.getLstLocations().get(i).getBuilding().getCode();
        }
        buildings[0] = "Please select a building";
        ArrayAdapter<String> dataAdapterBuilding = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, buildings);
        dataAdapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBuilding.setAdapter(dataAdapterBuilding);


        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {

        spSite.setEnabled(false);
        spBuilding.setEnabled(false);
        spCustomer.setEnabled(false);
        for (int i = 0; i < customers.length; i++) {
            if (customer.getCode().toLowerCase().equals(spCustomer.getItemAtPosition(i).toString().toLowerCase())) {
                spCustomer.setSelection(i);
                break;
            }
        }


        spLocation.setEnabled(false);
        for (int i = 0; i < locations.length; i++) {
            if (fireBugEquipment.getLocation().getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                spLocation.setSelection(i);
                tvDescprition.setText(realmSyncGetResponseDTO.getLstLocations().get(i-1).getDescription());

                for (int j = 0; j < sites.length; j++) {
                    if (realmSyncGetResponseDTO.getLstLocations().get(i-1).getSite().getCode().toLowerCase().equals(spSite.getItemAtPosition(j).toString().toLowerCase())) {
                        spSite.setSelection(j);
                        break;
                    }
                }

                for (int k = 0; k < buildings.length; k++) {
                    if (realmSyncGetResponseDTO.getLstLocations().get(i-1).getBuilding().getCode().toLowerCase().equals(spBuilding.getItemAtPosition(k).toString().toLowerCase())) {
                        spBuilding.setSelection(i);
                        break;
                    }
                }

                break;
            }
        }



    }


    void setViewForVAddAsset() {
        spLocation.setEnabled(true);
        spLocation.setSelection(0);
        tvDescprition.setText("No location selected");
        spSite.setEnabled(false);
        spBuilding.setEnabled(false);
        spSite.setSelection(0);
        spBuilding.setSelection(0);
        spCustomer.setEnabled(true);
        spCustomer.setSelection(0);

    }

    private void initListeners() {
        spLocation.setOnItemSelectedListener(this);
        spCustomer.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spLocation: {
                posLoc = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (null != DataManager.getInstance().getLocation(strSelectedState)) {
                    for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
                        if (DataManager.getInstance().getLocation(strSelectedState).getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                            spLocation.setSelection(i);
                            tvDescprition.setText(DataManager.getInstance().getLocation(strSelectedState).getDescription());
                            for (int k = 0; k < sites.length;k++) {
                                if (DataManager.getInstance().getLocation(strSelectedState).getSite().getCode().toLowerCase().equals(spSite.getItemAtPosition(k).toString().toLowerCase())) {
                                    spSite.setSelection(k);
                                    break;
                                }
                            }

                            for (int j = 0; j < buildings.length; j++) {
                                if (DataManager.getInstance().getLocation(strSelectedState).getBuilding().getCode().toLowerCase().equals(spBuilding.getItemAtPosition(j).toString().toLowerCase())) {
                                    spBuilding.setSelection(j);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }else{
                    setViewForVAddAsset();
                }

                try {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            break;

            case R.id.spCustomer: {
                posCustomer = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                try {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
