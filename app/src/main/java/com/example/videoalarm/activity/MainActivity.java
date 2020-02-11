package com.example.videoalarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.videoalarm.R;
import com.example.videoalarm.fragment.HomeAddFragment;
import com.example.videoalarm.fragment.HomeFragment;
import com.example.videoalarm.fragment.PlayFragment;
import com.example.videoalarm.fragment.SearchFragment;
import com.example.videoalarm.fragment.ProfileFragment;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.utils.AlarmReceiver;
import com.example.videoalarm.utils.MyDebug;
import com.example.videoalarm.utils.SqlLiteUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    /*
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

     */

    private HomeFragment homeFragment = new HomeFragment();
    private HomeAddFragment homeAddFragment = new HomeAddFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private PlayFragment playFragment = new PlayFragment();

    private BottomNavigationView menuBottom;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();

        menuBottom = findViewById(R.id.menu_bottom);

        setFragment(null, homeFragment);

        menuBottom.setSelectedItemId(R.id.home);
        menuBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.isChecked()) {
                    return true;
                } else {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:
                            setFragment(null, homeFragment);
                            getSupportActionBar().setTitle("Home");
                            return true;
                        case R.id.menu_home_add:
                            setFragment(null, homeAddFragment);
                            getSupportActionBar().setTitle("HomeAdd");
                            return true;
                        case R.id.menu_playlist:
                            setFragment(null, searchFragment);
                            getSupportActionBar().setTitle("HomeAdd");
                            return true;
                        case R.id.menu_profile:
                            //setFragment(profileFragment);
                            setFragment(null, playFragment);
                            getSupportActionBar().setTitle("Profile");
                            return true;
                        default:
                            setFragment(null, homeFragment);
                            getSupportActionBar().setTitle("Home");
                            return true;
                    }
                }
            }
        });
    }

    // Fragment전환
    public void setFragment(Bundle bundle,  Fragment fragment) {
        MyDebug.log( homeAddFragment == null ? "false" : "true");
        MyDebug.log("# HomeFragment : # " + fragment);
        MyDebug.log( "fragment ID : "  + fragment.getId());
        MyDebug.log( "fragment LIFECYCLE : "  + fragment.getLifecycle());
        MyDebug.log( "fragment Context : "  + fragment.getContext());

        ft = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
    }

    /* // HomeAddFragment 처리.
    public void setHomeAddFragment(Bundle bundle) {
        ft = getSupportFragmentManager().beginTransaction();
        MyDebug.log( homeAddFragment == null ? "false" : "true");
        MyDebug.log( "home Add fragment ID : "  + homeAddFragment.getId());
        MyDebug.log( "home Add LIFECYCLE  : "  + homeAddFragment.getLifecycle());
        MyDebug.log( "home Add Context : "  + homeAddFragment.getContext());

        homeAddFragment.setArguments(bundle);
        ft.replace(R.id.main_frame, homeAddFragment);
        ft.commit();
    }

     */
    //--------------------------------------------------------------------------------------------//
    // DB 초기화
    //--------------------------------------------------------------------------------------------//
    private void initDatabase() {
        SqlLiteUtil.getInstance().setInitView(getApplicationContext(), "ALARM");
    }
}
