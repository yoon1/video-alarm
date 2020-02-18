package com.example.videoalarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

import java.util.Calendar;

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

    private Intent intent;
    private AlarmManager alarmManager;
    private Button main_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDebug.log("Main Activity 진입.");
        initDatabase();
        setFragment(null, homeFragment);

        menuBottom = findViewById(R.id.menu_bottom);
        intent = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        /* alarm SETTING END */
        MyDebug.log("Main Activity.. 말잇못");

        menuBottom.setSelectedItemId(R.id.home);
        menuBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.isChecked()) {
                    return true;
                } else {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:
                            MyDebug.log("HOMEFRAGMETN ::");
                            setFragment(null, homeFragment);
                            getSupportActionBar().setTitle("Home");
                            return true;
                        case R.id.menu_home_add:
                            MyDebug.log("HOMEADDFRAGMENT ::");
                            setFragment(null, homeAddFragment);
                            getSupportActionBar().setTitle("HomeAdd");
                            return true;
                        case R.id.menu_playlist:
                            MyDebug.log("PLAYER ::");
                            setFragment(null, searchFragment);
                            getSupportActionBar().setTitle("HomeAdd");
                            return true;
                        case R.id.menu_profile:
                            MyDebug.log("MENU ::");
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
        MyDebug.log("# SET FRAGMENT : # " + fragment);

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

    //--------------------------------------------------------------------------------------------//
    // registerAlarm 추가.
    //--------------------------------------------------------------------------------------------//
    public void registerAlarm(Alarm alarm) {
        // Receiver 연결 intent.
        MyDebug.log("registerAlarm " );
        // 시간 setting.
        /* Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5); */
        Calendar cal = Calendar.getInstance();

        //cal.add(Calendar.HOUR_OF_DAY, alarm.getAlarmTimeHour());
        int hour = alarm.getAlarmTimeHour();
        int minute = alarm.getAlarmTimeMin();
        MyDebug.log("registerAlarm 등록된 알람  :: " + hour  + " : " + minute );
        cal.set (cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
        MyDebug.log("registerAlarm 2 :: " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE ) );

        intent.putExtra("id", Integer.toString(alarm.getId()));
        intent.putExtra("videoId", alarm.getVideoId());
        MyDebug.log("registerAlarm Intent Id :: "+ alarm.getId());
        MyDebug.log("registerAlarm Intent videoId :: "+ alarm.getVideoId());


        MyDebug.log("registerAlarm 3" );
        intent.putExtra("state", "alarm on");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // kitkat : 4.4ver
            MyDebug.log("registerAlarm 4" );
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),alarmIntent);
        } */
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
    }

    //--------------------------------------------------------------------------------------------//
    // unRegisterAlarm 추가.
    //--------------------------------------------------------------------------------------------//
    public void unRegisterAlarm(int alarmId)
    {
        MyDebug.log("unregisterAlarm : " + alarmId );
        intent.putExtra("state", "alarm off");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, alarmId, intent, 0);
        //alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if ( alarmIntent != null ) {
            MyDebug.log("unRegisterAlarm Success!");
            alarmManager.cancel(alarmIntent);
        } else {
          MyDebug.log("알람이 없어서 unRegisterAlarm 실패!");
        }

    }
}
