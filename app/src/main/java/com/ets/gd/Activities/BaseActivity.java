package com.ets.gd.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ets.gd.Fragments.DashboardFragment;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.R;


public class BaseActivity extends AppCompatActivity
        implements FragmentDrawer.FragmentDrawerListener {

    private static FragmentManager fragmentManager;
    static private MenuItem searchMenuItem;
    private static DashboardFragment dashboardFragment;
    private Toolbar toolbar;
    private FragmentDrawer drawerFragment;
    private TextView tbTitleTop;
    private TextView tbTitleBottom;
    private DrawerLayout drawer;
    String title;

    public static void refreshMainViewByNew(Fragment fragment) {
        if (fragment instanceof DashboardFragment) {
            //searchMenuItem.setVisible(false);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_body,
                            dashboardFragment).commit();
        }
        fragmentManager.executePendingTransactions();
    }

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
        setSupportActionBar(toolbar);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_body,
                        new DashboardFragment()).commit();
    }

    private void initListeners() {
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }*/

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
            //super.onBackPressed();
            //Toast.makeText(this, "uncomment onBackPressed or override for back functionality", Toast.LENGTH_SHORT).show();
        }
    }

/*    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            searchMenuItem.setVisible(false);
            tbTitleTop.setText("ETS");
            tbTitleBottom.setText("Dashboard");
            refreshMainViewByNew(new DashboardFragment());

        } else if (id == R.id.nav_firebug) {
           *//* searchMenuItem.setVisible(true);
            tbTitleTop.setText("Firebug");
            tbTitleBottom.setText("Select Company");
            refreshMainViewByNew(new CustomerFragment());*//*

        } else if (id == R.id.nav_toolhawk) {
//            searchMenuItem.setVisible(true);
//            tbTitleTop.setText("Toolhawk");
//            tbTitleBottom.setText("Select Company");
//            refreshMainViewByNew(new CustomerFragment());

        } else if (id == R.id.nav_deviceinfo) {
            tbTitleTop.setText("ETS");
            tbTitleBottom.setText("Device Info");

        } else if (id == R.id.nav_logout) {
            finish();
            startActivity(new Intent(BaseActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/


    private void displayView(int position) {
        Fragment fragment = null;
        title = getString(R.string.app_name);
        switch (position) {
            case 0:
                tbTitleTop.setText("ETS");
                tbTitleBottom.setText("Dashboard");
                refreshMainViewByNew(new DashboardFragment());
                break;
            case 1:
                tbTitleTop.setText("Firebug");
                tbTitleBottom.setText("Select Company");
                break;


            case 2:
                tbTitleTop.setText("Toolhawk");
                tbTitleBottom.setText("Select Company");
                break;



            case 3:
                tbTitleTop.setText("ETS");
                tbTitleBottom.setText("Inventory");
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
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container_body);
        searchMenuItem = menu.findItem(R.id.activity_customer_search);
        searchMenuItem.setVisible(false);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
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
//                if (newText != null) {
//                    customerFragment.updateAdapterForSearchKey(newText);
//                    if (currentFragment instanceof CustomerFragment) {
//                        customerFragment.updateAdapterForSearchKey(newText);
//                    }
//                    return false;
//                }
                return true;

            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.activity_customer_search:
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

