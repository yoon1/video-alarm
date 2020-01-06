package com.example.videoalarm.fragment;

import android.app.AlarmManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoalarm.R;
import com.example.videoalarm.activity.MainActivity;
import com.example.videoalarm.adapter.AdapterHome;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.utils.MyDebug;
import com.example.videoalarm.utils.VideoAlarmManagerUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    List<Alarm> alarmList = new ArrayList<Alarm>();
    private LinearLayoutManager manager;
    VideoAlarmManagerUtil videoAlarmManager = VideoAlarmManagerUtil.getInstance();
    Button btn_add;
    RecyclerView recyclerView;
    TextView listnullText;

    public static HomeFragment newInstance() {
        return new HomeFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btn_add = view.findViewById(R.id.addButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        listnullText = view.findViewById(R.id.listnullText);

        // 알람 리스트 사이즈가 0일 때.
        if(false) {
            MyDebug.log("alarmList IS NULL");
            recyclerView.setVisibility(View.GONE);
            listnullText.setVisibility(View.VISIBLE);
        } else {
            // 알람 리스트 사이즈가 0이 아닐 떄.
            MyDebug.log("alarmList IS NOT NULL");
            recyclerView.setVisibility(View.VISIBLE);
            listnullText.setVisibility(View.GONE);

            //alarmList = videoAlarmManager.GetAlarmList();
            // test 용
             {
                Alarm tAlarm = new Alarm();
                alarmList.add(tAlarm);
            }
            AdapterHome adapter = new AdapterHome(getContext(), alarmList);
            manager = new LinearLayoutManager(getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
        }

        // 추가 버튼 클릭.
        btn_add .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton();
            }
        });
        //--------------------------------------------------------------------------------------------//
        // 알람 저장 : 예외 처리 필요
        //--------------------------------------------------------------------------------------------//

        return view;
    }
    
    public void saveButton() {
        //Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).setFragment(HomeAddFragment.newInstance());
    }
 }
