package com.lee.halu.du_an_1_mob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.halu.du_an_1_mob.Adapter.OrderAdapter;
import com.lee.halu.du_an_1_mob.Model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DiagramFragment diagramFragment;
    private ListOrderFragment listOrderFragment;
    private ZoneFragment zoneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        zoneFragment = new ZoneFragment();
        diagramFragment = new DiagramFragment();
        listOrderFragment = new ListOrderFragment();
        hienthimhsodo();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            hienthimhlistorder();
        } else if (id == R.id.nav_gallery) {
            hienthimhsodo();
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(HomeActivity.this, ListBillActivity.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(HomeActivity.this, StatisticalActivity.class));

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(HomeActivity.this, ChoseCreateDataActivity.class));

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

            // Tao su kien ket thuc app
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hienthisodo(View view) {
        hienthimhsodo();
    }

    private void hienthimhsodo() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (diagramFragment.isAdded() && zoneFragment.isAdded()) { //neu co chi hien thi
            ft.show(diagramFragment);
            ft.show(zoneFragment);
        } else { //khong co --> them vao
            ft.add(R.id.container, diagramFragment);
            ft.add(R.id.container1, zoneFragment);
        }

        if (listOrderFragment.isAdded()) { //neu dang hien thi --> an no di
            ft.hide(listOrderFragment);
        }

        //sau khi thay doi fragment--> phai xac thuc
        ft.commit();
    }

    public void hienthilistorder(View view) {
        hienthimhlistorder();
    }

    private void hienthimhlistorder() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (listOrderFragment.isAdded()) { //neu co chi hien thi
            ft.show(listOrderFragment);
        } else { //khong co --> them vao
            ft.add(R.id.container, listOrderFragment);
        }

        if (diagramFragment.isAdded() && zoneFragment.isAdded()) { //neu dang hien thi --> an no di
            ft.hide(diagramFragment);
            ft.hide(zoneFragment);
        }

        //sau khi thay doi fragment--> phai xac thuc
        ft.commit();
    }
}
