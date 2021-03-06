package com.and.pepapig;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private NavController navController;
    private AppBarConfiguration configuration;
    private int count = 0;
    private TextView language_tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setIcon(R.drawable.ic_iconmonstr_warning_2);
        alert.setTitle(R.string.home_dia_title);
        alert.setMessage(R.string.homepage_dia_info);
        alert.setPositiveButton(R.string.home_dia_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();

        initViews();
        setupNavigation();
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.side_navmenu);
        bottomNavigationView = findViewById(R.id.bottom_navbar);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setSupportActionBar(toolbar);

        configuration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_bottom_mapPage)
                .setOpenableLayout(drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        setBottomNavigationView();
    }

    private void setBottomNavigationView() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            final int id = destination.getId();
            if (id == R.id.nav_home || id == R.id.nav_bottom_mapPage || id == R.id.nav_user) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                bottomNavigationView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, configuration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_share) {
            Toast.makeText(MainActivity.this, R.string.top_toast_share, Toast.LENGTH_LONG).show();
            Intent intent_share = new Intent(Intent.ACTION_SEND);
            intent_share.setType("text/plain");
            intent_share.putExtra(Intent.EXTRA_EMAIL, new String[]{"fhuurr@163.com"});
            intent_share.putExtra(Intent.EXTRA_SUBJECT, "This is a test email to you");
            intent_share.putExtra(Intent.EXTRA_TEXT, "Hello world");
            startActivity(intent_share);
        } else if (itemId == R.id.action_like) {
            if (count < 1) {
                item.setIcon(R.drawable.ic_iconmonstr_thumb_15);
                Toast.makeText(MainActivity.this, R.string.top_toast_like, Toast.LENGTH_SHORT).show();
                count++;
            } else
                Toast.makeText(MainActivity.this, R.string.top_toast_like2, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void calculator_change_language(View v){
        String system_language = Locale.getDefault().getLanguage();  // ??????????????????
        language_tag = findViewById(R.id.language_tag);

        // ???if???????????????????????????????????????????????????
        if (system_language.equals("zh")){
            language_tag.setText(R.string.s_language_text_en);
            Locale.setDefault(Locale.ENGLISH);
            Configuration configuration = getBaseContext().getResources().getConfiguration();
            configuration.setLocale(Locale.ENGLISH);
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
            recreate();
        } else {
            language_tag.setText(R.string.s_language_text_cn);
            Locale.setDefault(Locale.CHINESE);
            Configuration configuration = getBaseContext().getResources().getConfiguration();
            configuration.setLocale(Locale.CHINESE);
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
            recreate();
        }
    }
}