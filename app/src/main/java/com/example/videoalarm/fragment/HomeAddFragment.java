package com.example.videoalarm.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.videoalarm.R;
import com.example.videoalarm.activity.MainActivity;
import com.example.videoalarm.dialog.DateDialogFragment;
import com.example.videoalarm.dialog.TimeDialogFragment;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.utils.MyDebug;
import com.example.videoalarm.utils.VideoAlarmManagerUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import static android.content.ContentValues.TAG;

public class HomeAddFragment extends Fragment {
    private View view_date;
    private View view_time;

    private Button btn_save;
    private Button btn_cancle;
    private ImageView iv_home_thumbnail;
    private TextView txt_date;
    private TextView txt_time;
    private EditText txt_content;

    private TextView txt_time_temp;
    private TextView txt_date_temp;

    private String getThumb;
    private String id;
    private String videoId;
    private String videoName;

    int year;
    int day;
    int month;
    int hour;
    int minute;

    public static HomeAddFragment newInstance() {
        return new HomeAddFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_add_alarm, container, false);
        view_date = view.findViewById(R.id.dateView);
        view_time = view.findViewById(R.id.timeView);
        btn_save = view.findViewById(R.id.saveButton);
        btn_cancle = view.findViewById(R.id.cancleButton);
        iv_home_thumbnail = view.findViewById(R.id.iv_home_add_thumb);
        txt_content = view.findViewById(R.id.contentText);
        txt_date = view.findViewById(R.id.dateText);
        txt_time = view.findViewById(R.id.timeText);

        txt_date_temp = view.findViewById(R.id.dateTxt_temp);
        txt_time_temp = view.findViewById(R.id.timeTxt_temp);

        txt_date_temp.setVisibility(View.INVISIBLE);
        txt_time_temp.setVisibility(View.INVISIBLE);

        if (getArguments() != null) {
            MyDebug.log("SET ALARM DATE!");
            setAlarmData();
        } else {
            MyDebug.log("Argument is null");
        }

        getThumb = "https://img.youtube.com/vi/" + videoId + "/" + "hqdefault.jpg";
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
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleButton();
            }
        });
        iv_home_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setYoutubeVideo();
            }
        });
        Picasso.get()
                .load(getThumb)
                .placeholder(R.drawable.default_video)
                .fit()
                .centerCrop()
                .into(iv_home_thumbnail, new Callback() {
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
        Alarm alarm = new Alarm();
        alarm = getCurrentAlarm();
        if (getArguments().getString("id") != null && !getArguments().getString("id").isEmpty()) {
            VideoAlarmManagerUtil.getInstance().UpdateAlarm(alarm);
            ((MainActivity) getActivity()).unRegisterAlarm(alarm.getId());
            ((MainActivity) getActivity()).registerAlarm(alarm);
        } else {
            int result = (int) VideoAlarmManagerUtil.getInstance().AddAlarm(alarm);
            alarm.setId(result);
            ((MainActivity) getActivity()).registerAlarm(alarm);
        }
        ((MainActivity) getActivity()).setFragment(null, HomeFragment.newInstance());
    }

    //--------------------------------------------------------------------------------------------//
    // 알람 설정 취소.
    //--------------------------------------------------------------------------------------------//
    public void cancleButton() {
        /* 2. 되돌아가기 */
        ((MainActivity) getActivity()).setFragment(null, HomeFragment.newInstance());
        //onDestroyView();
    }

    //--------------------------------------------------------------------------------------------//
    // 알람 Video 설정
    //--------------------------------------------------------------------------------------------//
    public void setYoutubeVideo() {
        /* *******************
                Bundle
         ******************  */
        Bundle bundle = new Bundle();
        Alarm alarm = getCurrentAlarm();
        try {
            if (alarm.getAlarmDate() != null && !alarm.getAlarmDate().isEmpty())
                bundle.putString("alarmDate", alarm.getAlarmDate());
            if (alarm.getAlarmTime() != null && !alarm.getAlarmTime().isEmpty())
                bundle.putString("alarmTime", alarm.getAlarmTime());
            if (alarm.getAlarmNote() != null && !alarm.getAlarmNote().isEmpty())
                bundle.putString("alarmNote", alarm.getAlarmNote());
            if (alarm.getVideoId() != null && !alarm.getVideoId().isEmpty())
                bundle.putString("videoId", videoId);
            if (alarm.getVideoName() != null && !alarm.getVideoName().isEmpty())
                bundle.putString("videoName", videoName);
            if (getArguments().getString("id") != null && !getArguments().getString("id").isEmpty())
                bundle.putString("id", getArguments().getString("id"));
        } catch (NullPointerException e) {
            MyDebug.log("NULL ERROR : ", e.toString());
        }
        SearchFragment searchFragment = new SearchFragment();
        ((MainActivity) getActivity()).setFragment(bundle, searchFragment.newInstance());
    }

    //--------------------------------------------------------------------------------------------//
    // setting된 Alarm 반환
    //--------------------------------------------------------------------------------------------//
    private Alarm getCurrentAlarm() {
        Alarm alarm = new Alarm();
        if (id != null && !id.isEmpty() && id != "-1") {
            MyDebug.log("GET CURRENT ALARM : " + id);
            alarm.setId(Integer.parseInt(id));
        }
        alarm.setEnable(true);
        alarm.setAlarmDate(txt_date_temp.getText().toString());
        alarm.setAlarmTime(txt_time_temp.getText().toString());
        alarm.setAlarmNote(txt_content.getText().toString());
        alarm.setVideoId(videoId);
        alarm.setVideoName(videoName);
        MyDebug.log("getCurrentAlarm");
        alarm.printAlarm();
        return alarm;
    }

    //--------------------------------------------------------------------------------------------//
    // DB : 조회한 Alarm 데이터 setting
    //--------------------------------------------------------------------------------------------//
    private void setAlarmData() {
        if (getArguments().getString("id") != null && !getArguments().getString("id").isEmpty()) {
            id = getArguments().getString("id");
        }
        if (getArguments().getString("alarmDate") != null && !getArguments().getString("alarmDate").isEmpty()) {
            txt_date.setText(getFormatDate(getArguments().getString("alarmDate")));
            txt_date_temp.setText(getArguments().getString("alarmDate"));
        }
        if (getArguments().getString("alarmTime") != null && !getArguments().getString("alarmTime").isEmpty()) {
            txt_time.setText(getFormatTime(getArguments().getString("alarmTime")));
            txt_time_temp.setText(getArguments().getString("alarmTime"));
        }
        if (getArguments().getString("alarmNote") != null && !getArguments().getString("alarmNote").isEmpty()) {
            txt_content.setText(getArguments().getString("alarmNote"));
        }
        if (getArguments().getString("videoId") != null && !getArguments().getString("videoId").isEmpty()) {
            videoId = getArguments().getString("videoId");
        }
        if (getArguments().getString("videoName") != null && !getArguments().getString("videoName").isEmpty()) {
            videoName = getArguments().getString("videoName");
        }
    }

    public String getFormatDate(String date) {
        String date_val = "";
        try {
            if (date != null) {
                date_val = date.substring(0, 4);
                date_val += "년 ";
                date_val += date.substring(4, 6);
                date_val += "월 ";
                date_val += date.substring(6);
                date_val += "일";
            }
        } catch (IndexOutOfBoundsException e) {
            MyDebug.log("Exception : " + e);
        }
        return date_val;
    }

    public String getFormatTime(String time) {
        String time_val = "";
        try {
            if (time != null) {
                time_val = time.substring(0, 2);
                time_val += "시 ";
                time_val += time.substring(2, 4);
                time_val += "분";
            }
        } catch (IndexOutOfBoundsException e) {
            MyDebug.log("Exception : " + e);
        }
        return time_val;
    }
}