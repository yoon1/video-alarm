package com.example.videoalarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import com.example.videoalarm.R;
import com.example.videoalarm.fragment.HomeAddFragment;
import com.example.videoalarm.fragment.HomeFragment;
import com.example.videoalarm.fragment.SearchFragment;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.utils.AlarmReceiver;
import com.example.videoalarm.utils.MyDebug;
import com.example.videoalarm.utils.SqlLiteUtil;

import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment = new HomeFragment();
    private HomeAddFragment homeAddFragment = new HomeAddFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private FragmentTransaction ft;

    private Intent intent;
    private AlarmManager alarmManager;
    private Button main_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();
        setFragment(null, homeFragment);
        intent = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }


    @Override
    public void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isActive", false).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isActive", false).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isActive", true).commit();
    }

    // Fragment전환
    public void setFragment(Bundle bundle,  Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
    }

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
        MyDebug.log("REGISTER ALARM ID : " + alarm.getId());
        Calendar cal = Calendar.getInstance();
        int hour = alarm.getAlarmTimeHour();
        int minute = alarm.getAlarmTimeMin();
        cal.set (cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
        intent.putExtra("id", Integer.toString(alarm.getId()));
        intent.putExtra("videoId", alarm.getVideoId());
        intent.putExtra("alarmNote", alarm.getAlarmNote());
        intent.putExtra("state", "alarm on");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
    }

    //--------------------------------------------------------------------------------------------//
    // unRegisterAlarm 추가.
    //--------------------------------------------------------------------------------------------//
    public void unRegisterAlarm(int alarmId)
    {
        MyDebug.log("UNREGISTER ALARM ID : " + alarmId);
        intent.putExtra("state", "alarm off");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, alarmId, intent, 0);
        if ( alarmIntent != null ) {
            alarmManager.cancel(alarmIntent);
        } else {
          MyDebug.log("alarmId is null");
        }
    }
}
