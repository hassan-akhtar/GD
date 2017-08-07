package com.ets.gd.Activities.Other;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.Login.LoginActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Customer.CustomerFragment;
import com.ets.gd.Fragments.DashboardFragment;
import com.ets.gd.Fragments.DeviceInfoFragment;
import com.ets.gd.FireBug.Fragments.FirebugDashboardFragment;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Inventory.Fragments.InventoryDashboardFragment;
import com.ets.gd.Activities.Sync.SyncFragment;
import com.ets.gd.NetworkLayer.ResponseDTOs.PermissionType;
import com.ets.gd.ToolHawk.Fragments.ToolhawkDashboardFragment;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Fragments.ToolhawkDashboardFragmentNew;
import com.ets.gd.Utils.SharedPreferencesManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends AppCompatActivity
        implements FragmentDrawer.FragmentDrawerListener {

    SharedPreferencesManager sharedPreferencesManager;
    private static FragmentManager fragmentManager;
    static private MenuItem searchMenuItem;
    private static DashboardFragment dashboardFragment;
    private Toolbar toolbar;
    private FragmentDrawer drawerFragment;
    static private TextView tbTitleTop;
    private static TextView tbTitleBottom;
    private DrawerLayout drawer;
    View llDeviceInfo, llSync, llLogout;
    public static Fragment currentFragment;
    String title;
    TextView username;
    public static boolean isSearching = false;
    private List<PermissionType> rolePermissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initViews();
        initObj();
        initListeners();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.myToolBar).findViewById(R.id.toolbar);
        toolbar.setTitle("");
        tbTitleTop = (TextView) toolbar.findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) toolbar.findViewById(R.id.tbTitleBottom);
        tbTitleTop.setText("ETS");
        tbTitleBottom.setText("Dashboard");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dashboardFragment = new DashboardFragment();
    }

    private void initObj() {
        fragmentManager = getSupportFragmentManager();
        sharedPreferencesManager = new SharedPreferencesManager(BaseActivity.this);
        setSupportActionBar(toolbar);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        llSync = drawer.findViewById(R.id.llSync);
        llDeviceInfo = drawer.findViewById(R.id.llDeviceInfo);
        llLogout = drawer.findViewById(R.id.llLogout);
        username = (TextView) drawer.findViewById(R.id.username);
        username.setText(sharedPreferencesManager.getString(SharedPreferencesManager.LOGGED_IN_USERNAME));
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.SYNC_STATE)) {
            tbTitleTop.setText("ETS");
            tbTitleBottom.setText("Sync");
            refreshMainViewByNew(new SyncFragment());
        } else {
            displayView(0);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            new DashboardFragment()).commit();
        }

    }

    private void initListeners() {
        llSync.setOnClickListener(mGlobal_OnClickListener);
        llDeviceInfo.setOnClickListener(mGlobal_OnClickListener);
        llLogout.setOnClickListener(mGlobal_OnClickListener);
    }


    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.llSync: {
                    drawer.closeDrawer(Gravity.LEFT);
                    tbTitleTop.setText("ETS");
                    tbTitleBottom.setText("Sync");
                    refreshMainViewByNew(new SyncFragment());
                    break;
                }

                case R.id.llDeviceInfo: {
                    drawer.closeDrawer(Gravity.LEFT);
                    refreshMainViewByNew(new DeviceInfoFragment());
                    break;
                }

                case R.id.llLogout: {
                    Intent in = new Intent(BaseActivity.this,
                            LoginActivity.class);
                    startActivity(in);
                    finish();
                    break;
                }
            }
        }

    };


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void refreshMainViewByNew(Fragment fragment) {

        if (fragment instanceof DashboardFragment) {
            //searchMenuItem.setVisible(false);

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            dashboardFragment).commit();
            tbTitleTop.setText("ETS");
            tbTitleBottom.setText("Dashboard");
        } else if (fragment instanceof CustomerFragment) {
            //searchMenuItem.setVisible(false);
            tbTitleTop.setText("Firebug");
            tbTitleBottom.setText("Select Company");
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            new CustomerFragment()).commit();
        } else if (fragment instanceof FirebugDashboardFragment) {
            //searchMenuItem.setVisible(false);
            tbTitleTop.setText("Firebug");
            tbTitleBottom.setText("Menu");
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            new FirebugDashboardFragment()).commit();
        } else if (fragment instanceof ToolhawkDashboardFragment) {
            //searchMenuItem.setVisible(false);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            new ToolhawkDashboardFragment()).commit();
            tbTitleTop.setText("Toolhawk");
            tbTitleBottom.setText("Menu");
        } else if (fragment instanceof ToolhawkDashboardFragmentNew) {
            //searchMenuItem.setVisible(false);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            new ToolhawkDashboardFragmentNew()).commit();
            tbTitleTop.setText("Toolhawk");
            tbTitleBottom.setText("Menu");
        } else if (fragment instanceof InventoryDashboardFragment) {
            //searchMenuItem.setVisible(false);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            new InventoryDashboardFragment()).commit();
        } else if (fragment instanceof SyncFragment) {
            //searchMenuItem.setVisible(false);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            new SyncFragment()).commit();
        } else if (fragment instanceof DeviceInfoFragment) {
            //searchMenuItem.setVisible(false);
            tbTitleTop.setText("ETS");
            tbTitleBottom.setText("Device Info");
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            new DeviceInfoFragment()).commit();
        }
        fragmentManager.executePendingTransactions();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (currentFragment instanceof FirebugDashboardFragment) {
            refreshMainViewByNew(new CustomerFragment());
        } else if (currentFragment instanceof CustomerFragment || currentFragment instanceof ToolhawkDashboardFragmentNew || currentFragment instanceof InventoryDashboardFragment) {
            refreshMainViewByNew(new DashboardFragment());

        } else {

            new AlertDialog.Builder(BaseActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to Logout?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(BaseActivity.this,
                                    LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(getResources().getDrawable(R.drawable.ic_logout_new))
                    .show();
        }
    }


    private void displayView(int position) {
        Fragment fragment = null;
        title = getString(R.string.app_name);
        rolePermissions = DataManager.getInstance().getRolePermissionsByUserName(sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_USERNAME));
        switch (position) {

            case 0:
                tbTitleTop.setText("ETS");
                tbTitleBottom.setText("Dashboard");
                refreshMainViewByNew(new DashboardFragment());
                break;
            case 1:
                boolean accessFireBug = false;
                for(int i=0;i<rolePermissions.size();i++){
                    if( rolePermissions.get(i).getValue().equals("FireBug")){
                        accessFireBug = true;
                    }
                }

                if (accessFireBug) {
                    tbTitleTop.setText("Firebug");
                    tbTitleBottom.setText("Select Company");
                    if (DataManager.getInstance().isServiceCompany()) {
                        BaseActivity.refreshMainViewByNew(new CustomerFragment());
                    } else {
                        BaseActivity.refreshMainViewByNew(new FirebugDashboardFragment());
                        if (null != DataManager.getInstance().getFirstSyncCustomer()) {
                            if (0!=DataManager.getInstance().getFirstSyncCustomer().getCustomerId()) {
                                if (null!=DataManager.getInstance().getCustomerByID(DataManager.getInstance().getFirstSyncCustomer().getCustomerId())) {
                                    EventBus.getDefault().post(DataManager.getInstance().getCustomerByID(DataManager.getInstance().getFirstSyncCustomer().getCustomerId()).getCode());
                                }
                            }
                        }

                    }
                } else {
                    showToast("You don't have permission to use Firebug");
                }


                break;


            case 2:
                boolean accessToolhawk = false;
                for(int i=0;i<rolePermissions.size();i++){
                    if( rolePermissions.get(i).getValue().equals("ToolHawk")){
                        accessToolhawk = true;
                    }
                }

                if (accessToolhawk) {
                    tbTitleTop.setText("Toolhawk");
                    tbTitleBottom.setText("Dashboard");
                    refreshMainViewByNew(new ToolhawkDashboardFragmentNew());
                } else {
                    showToast("You don't have permission to use ToolHawk");
                }


                break;


            case 3:
                boolean accessInventory = false;
                for(int i=0;i<rolePermissions.size();i++){
                    if( rolePermissions.get(i).getValue().equals("Inventory")){
                        accessInventory = true;
                    }
                }

                if (accessInventory) {
                    tbTitleTop.setText("ETS");
                    tbTitleBottom.setText("Inventory");
                    refreshMainViewByNew(new InventoryDashboardFragment());
                } else {
                    showToast("You don't have permission to use Inventory");
                }

                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull final Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        final Fragment currentFragment = fragmentManager.findFragmentById(R.id.container_body);
        searchMenuItem = menu.findItem(R.id.mSearch);
        if (currentFragment instanceof CustomerFragment) {
            searchMenuItem.setVisible(true);
        } else {
            searchMenuItem.setVisible(false);
        }
        final CustomerFragment customerFragment = new CustomerFragment();
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();

        View closeButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearching = false;
                searchView.setQuery("", false);
                customerFragment.updateAdapterForSearchKey("refresh");
            }
        });
/*
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearching = false;
                Toast t = Toast.makeText(BaseActivity.this, "close", Toast.LENGTH_SHORT);
                t.show();

                return false;
            }
        });
*/

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                isSearching = false;
                customerFragment.updateAdapterForSearchKey("refresh");
                return true;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.i("onQueryTextSubmit: ", query);
//                customerFragment.updateAdapterForSearchKey(query);
//                if (currentFragment instanceof CustomerFragment) {
//                    customerFragment.updateAdapterForSearchKey(query);
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(@NonNull String newText) {
                if (!"".equals(newText)) {
                    isSearching = true;
                    if (currentFragment instanceof CustomerFragment) {
                        customerFragment.updateAdapterForSearchKey(newText);
                    }
                }

                return true;

            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.mSearch:
                // search action
                Log.i("onOptionsItemSelected: ", "search something");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
}

