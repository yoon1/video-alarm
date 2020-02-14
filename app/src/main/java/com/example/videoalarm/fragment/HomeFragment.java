package com.example.videoalarm.fragment;
import android.graphics.Canvas;
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
import com.example.videoalarm.utils.SqlLiteUtil;
import com.example.videoalarm.utils.SwipeController;
import com.example.videoalarm.utils.SwipeControllerActions;
import com.example.videoalarm.utils.VideoAlarmManagerUtil;
import com.example.videoalarm.utils.VideoPlayingService;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private AdapterHome adapter ;
    SwipeController swipeController = null;
    private List<Alarm> alarmList = new ArrayList<Alarm>();
    private LinearLayoutManager manager;
    private VideoAlarmManagerUtil videoAlarmManager;
    private Button btn_add;
    private RecyclerView recyclerView;
    private TextView listnullText;
    private FragmentManager fragmentManager;

    private String videoId;

    public static HomeFragment newInstance() {
        return new HomeFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        videoAlarmManager = VideoAlarmManagerUtil.getInstance();
        btn_add = view.findViewById(R.id.addButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        listnullText = view.findViewById(R.id.listnullText);

        // 알람 리스트 사이즈가 0일 때.
        if(!VideoAlarmManagerUtil.getInstance().SetAlarmList()) {
            MyDebug.log("alarmList IS NULL");
            recyclerView.setVisibility(View.GONE);
            listnullText.setVisibility(View.VISIBLE);
        } else {
            // 알람 리스트 사이즈가 0이 아닐 떄.
            MyDebug.log("alarmList IS NOT NULL");
            recyclerView.setVisibility(View.VISIBLE);
            listnullText.setVisibility(View.GONE);

            alarmList = videoAlarmManager.GetAlarmList();
            // test 용
            /*{
                Alarm tAlarm = new Alarm();
                alarmList.add(tAlarm);
            }*/
            MyDebug.log("not null 인데 왜 아무것도 안찍히냐고ㅡㅡ");
            for (Alarm element : alarmList){
                MyDebug.log("-------------------------------");
                MyDebug.log("ID " + element.getId());
                MyDebug.log("ALARM TIME " + element.getAlarmTime());
                MyDebug.log("ALARM DATE" + element.getAlarmDate());
                MyDebug.log("ALARM NOTE" + element.getAlarmNote());
                MyDebug.log("VIDEO ID " + element.getVideoId());
                //MyDebug.log("VIDEO NAME" + element.getVideoName());
                MyDebug.log("-------------------------------");
            }

            adapter = new AdapterHome(getContext(), alarmList, this);

            manager = new LinearLayoutManager(getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);

            swipeController = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onLeftClicked(int position) {
                    super.onLeftClicked(position);
                    MyDebug.log("# EDIT : HomeFragment #");
                    updateButton(alarmList.get(position));
                }

                @Override
                public void onRightClicked(int position) {
                    super.onRightClicked(position);
                    MyDebug.log("# DELETE : HomeFragment #");
                    // delete 예외처리
                    if (position < 0 || position >= alarmList.size()) {
                        // Warning, invalid position
                    } else {
                        MyDebug.log("position : " + position);
                        MyDebug.log("alarmLIst position : : " + alarmList.get(position));
                        deleteButton(alarmList.get(position));
                        alarmList.remove(position);
                        recyclerView.getAdapter().notifyItemRemoved(position);
                    }
                }
            });

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
            itemTouchhelper.attachToRecyclerView(recyclerView);

            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    swipeController.onDraw(c);
                }
            });
        }

        // 추가 버튼 클릭.
        btn_add .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton();
            }
        });
        return view;
    }

    //--------------------------------------------------------------------------------------------//
    // 알람 저장 : 예외 처리 필요
    //--------------------------------------------------------------------------------------------//
    public void saveButton() {
        MyDebug.log("# HomeFragment : saveButton # ");
        ((MainActivity)getActivity()).setFragment(null, HomeAddFragment.newInstance());
    }

    //--------------------------------------------------------------------------------------------//
    // 알람 수정
    //--------------------------------------------------------------------------------------------//
    public void updateButton(Alarm target_alarm) {
        /* *******************
                Bundle
         ******************  */
        MyDebug.log("# HomeFragment : updateButton # ");
        Bundle bundle = new Bundle();
        MyDebug.log("전달 ALARMID : " +  target_alarm.getId());
        MyDebug.log("전달 alarmDate : " +  target_alarm.getAlarmDate());
        MyDebug.log("전달 getAlarmTime : " +  target_alarm.getAlarmTime());
        MyDebug.log("전달 getAlarmNote : " +  target_alarm.getAlarmNote());
        MyDebug.log("전달 getVideoId : " +  target_alarm.getVideoId());

        bundle.putString("alarmId" , Integer.toString(target_alarm.getId()));
        bundle.putString("alarmDate" , target_alarm.getAlarmDate());
        bundle.putString("alarmTime" , target_alarm.getAlarmTime());
        bundle.putString("alarmNote" , target_alarm.getAlarmNote());
        bundle.putString("videoId" , target_alarm.getVideoId());
        //bundle.putString("videoName", target_alarm.getVideoName());

        /*  DATA 전달 */
        ((MainActivity)getActivity()).setFragment(bundle, HomeAddFragment.newInstance());
    }

    public void deleteButton(Alarm alarm) {
        MyDebug.log("# HomeFragment : deleteButton # ");
        MyDebug.log("======DELETE ======");
        MyDebug.log("전달 ALARMID : " +  alarm.getId());
        MyDebug.log("전달 alarmDate : " +  alarm.getAlarmDate());
        MyDebug.log("전달 getAlarmTime : " +  alarm.getAlarmTime());
        MyDebug.log("전달 getAlarmNote : " +  alarm.getAlarmNote());
        MyDebug.log("전달 getVideoId : " +  alarm.getVideoId());
        MyDebug.log("======DELETE END ======");
        //DB : SqlLiteUtil.getInstance().delete(alarm.getId());
        VideoAlarmManagerUtil.getInstance().DeleteAlarm(alarm.getId());
    }
}
