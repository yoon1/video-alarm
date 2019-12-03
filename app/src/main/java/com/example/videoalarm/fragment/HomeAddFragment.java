package com.example.videoalarm.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoalarm.R;
import com.example.videoalarm.dialog.DateDialogFragment;
import com.example.videoalarm.dialog.TimeDialogFragment;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class HomeAddFragment extends Fragment {

    private View view_date;
    private View view_time;
    private Button btn_save;
    private ImageView iv_home_thumbnail;
    private TextView txt_date;
    private TextView txt_time;
    private EditText txt_content;

    public static HomeAddFragment newInstance() {
        return new HomeAddFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_add_alarm, container, false);
        view_date = view.findViewById(R.id.dateView);
        view_time = view.findViewById(R.id.timeView);
        btn_save = view.findViewById(R.id.saveButton);
        iv_home_thumbnail = view.findViewById(R.id.iv_home_add_thumb);
        txt_content = view.findViewById(R.id.contentText);
        txt_time = view.findViewById(R.id.timeText);
        txt_date = view.findViewById(R.id.dateText);

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
        Toast.makeText(getContext(), "You need to enter some text", Toast.LENGTH_SHORT).show();
    }
}