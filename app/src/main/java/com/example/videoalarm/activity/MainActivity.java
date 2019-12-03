package com.example.videoalarm.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.videoalarm.R;
import com.example.videoalarm.fragment.HomeAddFragment;
import com.example.videoalarm.fragment.HomeFragment;
import com.example.videoalarm.fragment.PlaylistFragment;
import com.example.videoalarm.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment = new HomeFragment();
    private HomeAddFragment homeAddFragment = new HomeAddFragment();
    private PlaylistFragment playlistFragment = new PlaylistFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private BottomNavigationView menuBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBottom = findViewById(R.id.menu_bottom);

        setFragment(homeFragment);
        menuBottom.setSelectedItemId(R.id.menu_home);
        menuBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.isChecked()) {
                    return true;
                } else {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:
                            setFragment(homeFragment);
                            getSupportActionBar().setTitle("Home");
                            return true;
                        /*case R.id.menu_playlist:
                            setFragment(playlistFragment);
                            getSupportActionBar().setTitle("Playlist");
                            return true;*/
                        case R.id.menu_playlist:
                            setFragment(homeAddFragment);
                            getSupportActionBar().setTitle("HomeAdd");
                            return true;
                        case R.id.menu_profile:
                            setFragment(profileFragment);
                            getSupportActionBar().setTitle("Profile");
                            return true;
                        default:
                            setFragment(homeFragment);
                            getSupportActionBar().setTitle("Home");
                            return true;
                    }
                }
            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
    }
}
