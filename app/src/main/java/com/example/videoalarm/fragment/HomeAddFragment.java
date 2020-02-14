
package com.example.videoalarm.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoalarm.R;
import com.example.videoalarm.activity.MainActivity;
import com.example.videoalarm.dialog.DateDialogFragment;
import com.example.videoalarm.dialog.TimeDialogFragment;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.utils.MyDebug;
import com.example.videoalarm.utils.SqlLiteUtil;
import com.example.videoalarm.utils.VideoAlarmManagerUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HomeAddFragment extends Fragment{
    private View view_date;
    private View view_time;
    private Button btn_save;
    private Button btn_cancle;
    private ImageView iv_home_thumbnail;
    private TextView txt_date;
    private TextView txt_time;
    private EditText txt_content;
    private String getThumb;
    private FragmentManager fragmentManager;

    private String alarmId;
    private String videoId;
    private String alarmDate;
    private String alarmTime;
    private String alarmNote;
    private String videoName;

    public static HomeAddFragment newInstance() {
        return new HomeAddFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_add_alarm, container, false);
        view_date = view.findViewById(R.id.dateView);
        view_time = view.findViewById(R.id.timeView);
        btn_save = view.findViewById(R.id.saveButton);
        btn_cancle = view.findViewById(R.id.cancleButton);
        iv_home_thumbnail = view.findViewById(R.id.iv_home_add_thumb);
        txt_content = view.findViewById(R.id.contentText);
        txt_time = view.findViewById(R.id.timeText);
        txt_date = view.findViewById(R.id.dateText);

        //getThumb = "https://img.youtube.com/vi/" + (String) alarm.getVideoId() + "/" + "hqdefault.jpg" ;
        if (getArguments() != null) {
            MyDebug.log("Argument존재.");
            setAlarmData();
        }
        MyDebug.log("Argument존재안함.");

        getThumb = "https://img.youtube.com/vi/" + videoId + "/" + "hqdefault.jpg" ;
        MyDebug.log("GETTHUMB LOG : " + getThumb);

        /* *******************
            Button Action
         ******************  */
        view_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateButton();
            }
        });

        view_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeButton();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cancleButton();
            }
        });
        iv_home_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 수정 필요 :: 수정하러 들어가게 해주세요.
                setYoutubeVideo();
            }
        });
        Picasso.get()
                .load(getThumb)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(iv_home_thumbnail, new Callback() { // 기본 이미지
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Thumbnail success");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "Thumbnail error");
                    }
                });

        return view;
    }

    // @Override public void onDestroyView() { super.onDestroyView(); }

    //--------------------------------------------------------------------------------------------//
    // 날짜 설정
    //--------------------------------------------------------------------------------------------//
    public void setDateButton() {
        DialogFragment newFragment = new DateDialogFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
    }

    //--------------------------------------------------------------------------------------------//
    // 시간 설정
    //--------------------------------------------------------------------------------------------//
    public void setTimeButton() {
        DialogFragment newFragment = new TimeDialogFragment();
        newFragment.show(getFragmentManager(), "TimePicker");
    }

    //--------------------------------------------------------------------------------------------//
    // 알람 저장 : 예외 처리 필요
    //--------------------------------------------------------------------------------------------//
    public void saveButton() {
        //Toast.makeText(getContext(), "You need to enter some text", Toast.LENGTH_SHORT).show();
        MyDebug.log("saveButton!");
        /* 1. transaction 처리 */

        // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Alarm alarm = new Alarm();
        alarm = getAlarm();

        MyDebug.log("ALARMID " +  alarmId);
        MyDebug.log("UPDATE ID : " +  alarm.getId());
        MyDebug.log("UPDATE DATE : " +  alarm.getAlarmDate());
        MyDebug.log("UPDATE TIEM : " +  alarm.getAlarmTime());
        MyDebug.log("UPDATE NOTE : " +  alarm.getAlarmNote());
        MyDebug.log("UPDATE VIDEOID: " +  alarm.getVideoId());
        MyDebug.log("UPDATE VIODEO NAME: " +  alarm.getVideoName());

        //alarm.setAlarmNote("ok?");
        if ( getArguments().getString("alarmId") != null && !getArguments().getString("alarmId").isEmpty()) {
            // DB : update(alarm);
            VideoAlarmManagerUtil.getInstance().UpdateAlarm(alarm);
            MyDebug.log("# UPDATE : HOMEADDFRAGMENT ");
        } else {
            // DB : insert(alarm);
            VideoAlarmManagerUtil.getInstance().AddAlarm(alarm);
            MyDebug.log("# INSERT : HOMEADDFRAGMENT ");
            //onDestroyView();
        }
        ((MainActivity)getActivity()).setFragment(null, HomeFragment.newInstance());

    }

    //--------------------------------------------------------------------------------------------//
    // 알람 설정 취소.
    //--------------------------------------------------------------------------------------------//
    public void cancleButton() {
        /* 2. 되돌아가기 */
        ((MainActivity)getActivity()).setFragment(null, HomeFragment.newInstance());
        //onDestroyView();
    }

    //--------------------------------------------------------------------------------------------//
    // 알람 Video 설정
    //--------------------------------------------------------------------------------------------//
    public void setYoutubeVideo() {
        /* *******************
                Bundle
         ******************  */
        /* 선택하면 video Id를 반환할 예정 */
        Bundle bundle = new Bundle();
        Alarm alarm = getAlarm();
        try {
            if ( alarm.getAlarmDate()!= null && !alarm.getAlarmDate().isEmpty() )
                bundle.putString("alarmDate", alarm.getAlarmDate());
            if ( alarm.getAlarmTime() != null && !alarm.getAlarmTime().isEmpty() )
                bundle.putString("alarmTime", alarm.getAlarmTime());
            if ( alarm.getAlarmNote()!= null && !alarm.getAlarmNote().isEmpty() )
                bundle.putString("alarmNote", alarm.getAlarmNote());
            if ( alarm.getVideoId() != null && !alarm.getVideoId().isEmpty() )
                bundle.putString("videoId", videoId);
            if ( alarm.getVideoName() != null && !alarm.getVideoName().isEmpty() )
                bundle.putString("videoName", videoName);
            if (getArguments().getString("alarmId") != null && !getArguments().getString("alarmId").isEmpty())
                bundle.putString("alarmId", getArguments().getString("alarmId"));
        } catch(NullPointerException e) {
            MyDebug.log("NULL ERROR : ", e.toString());
        }
        /* videoId, Name도 null처리 해야되는데 쫌따하자. */
        MyDebug.log("HomeAddFragment시작시작=====>>> ");
        MyDebug.log("AlarmDate "+ alarm.getAlarmDate());
        MyDebug.log("AlarmTime "+ alarm.getAlarmTime());
        MyDebug.log("AlarmNote"+ alarm.getAlarmNote());
        MyDebug.log("VideoId "+ alarm.getVideoId());
        MyDebug.log("VideoName"+ alarm.getVideoName());
        MyDebug.log("AlarmId "+ alarm.getId());
        MyDebug.log("HomeAddFragment종료종료=====>>> ");
        /*  DATA 전달 */

        SearchFragment searchFragment  = new SearchFragment();
        ((MainActivity)getActivity()).setFragment(bundle, searchFragment.newInstance());
    }

    //--------------------------------------------------------------------------------------------//
    // DB : insert Alarm
    //--------------------------------------------------------------------------------------------//
/*   private void insert(Alarm alarm) {
        SqlLiteUtil.getInstance().insert(alarm);
    }
 */

    //--------------------------------------------------------------------------------------------//
    // DB : update Alarm
    //--------------------------------------------------------------------------------------------//
/*     private void update(Alarm alarm) {
        SqlLiteUtil.getInstance().update(alarm);
    }
 */
    //--------------------------------------------------------------------------------------------//
    // setting된 Alarm 반환 - 이부분은 다시 고쳐야됌.
    //--------------------------------------------------------------------------------------------//
    private Alarm getAlarm() {
        Alarm alarm = new Alarm();
        if (alarmId != null && alarmId.isEmpty())
            alarm.setId(Integer.parseInt(alarmId));
        alarm.setEnable(true);
        alarm.setAlarmDate(txt_date.getText().toString());
        alarm.setAlarmTime(txt_time.getText().toString());
        alarm.setAlarmNote(txt_content.getText().toString());
        alarm.setVideoId(videoId);
        alarm.setVideoName(videoName);
        return alarm;
    }

    //--------------------------------------------------------------------------------------------//
    // DB : setAlarmDate!! >.<  ++ TODO : 초기값 설정.
    //--------------------------------------------------------------------------------------------//
    private void setAlarmData(){
        MyDebug.log("---------번들 출력----------");
        if ( getArguments().getString("alarmId") != null && !getArguments().getString("alarmId").isEmpty()) {
            MyDebug.log("alarmId : " + getArguments().getString("alarmId"));
            alarmId = getArguments().getString("alarmId");
        }
        if ( getArguments().getString("alarmDate") != null && !getArguments().getString("alarmDate").isEmpty()) {
            MyDebug.log("alarmDate : " + getArguments().getString("alarmDate"));
            txt_date.setText(getArguments().getString("alarmDate"));
        }
        if ( getArguments().getString("alarmTime") != null && !getArguments().getString("alarmTime").isEmpty()) {
            MyDebug.log("alarmTime : " + getArguments().getString("alarmTime"));
            txt_time.setText(getArguments().getString("alarmTime"));
        }
        if ( getArguments().getString("alarmNote") != null && !getArguments().getString("alarmNote").isEmpty()) {
            MyDebug.log("alarmNote : " + getArguments().getString("alarmNote"));
            txt_content.setText(getArguments().getString("alarmNote"));
        }
        if ( getArguments().getString("videoId") != null && !getArguments().getString("videoId").isEmpty()) {
            MyDebug.log("videoId : " + getArguments().getString("videoId"));
            videoId = getArguments().getString("videoId");
        }
        if ( getArguments().getString("videoName") != null && !getArguments().getString("videoName").isEmpty()) {
            MyDebug.log("videoName : " + getArguments().getString("videoName"));
            videoName = getArguments().getString("videoName");
        }
        MyDebug.log("-------------------------------");
    }

}