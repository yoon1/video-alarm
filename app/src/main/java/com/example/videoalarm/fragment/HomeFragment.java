package com.example.videoalarm.fragment;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.videoalarm.R;
import com.example.videoalarm.activity.MainActivity;
import com.example.videoalarm.adapter.AdapterHome;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.utils.MyDebug;
import com.example.videoalarm.utils.SwipeController;
import com.example.videoalarm.utils.SwipeControllerActions;
import com.example.videoalarm.utils.VideoAlarmManagerUtil;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
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

    private String videoId;

    public static HomeFragment newInstance() {
        return new HomeFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btn_add = view.findViewById(R.id.addButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        listnullText = view.findViewById(R.id.listnullText);
        videoAlarmManager = VideoAlarmManagerUtil.getInstance();

        // 알람 리스트 사이즈가 0일 때.
        if(!VideoAlarmManagerUtil.getInstance().SetAlarmList()) {
            recyclerView.setVisibility(View.GONE);
            listnullText.setVisibility(View.VISIBLE);
        } else {
            // 알람 리스트 사이즈가 0이 아닐 떄.
            recyclerView.setVisibility(View.VISIBLE);
            listnullText.setVisibility(View.GONE);

            alarmList = videoAlarmManager.GetAlarmList();
            adapter = new AdapterHome(getContext(), alarmList, this);
            manager = new LinearLayoutManager(getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
            swipeController = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onLeftClicked(int position) {
                    super.onLeftClicked(position);
                    MyDebug.log("HOME FRAGMENT -> START");
                    alarmList.get(position).printAlarm();
                    MyDebug.log("HOME FRAGMENT -> END ");
                    updateButton(alarmList.get(position));
                }

                @Override
                public void onRightClicked(int position) {
                    super.onRightClicked(position);
                    // delete 예외처리
                    if (position < 0 || position >= alarmList.size()) {
                        // Warning, invalid position
                    } else {
                        Alarm tAlarm = alarmList.get(position);
                        MyDebug.log("Home Fragment START ");
                        tAlarm.printAlarm();
                        MyDebug.log("Home Fragment END ");
                        deleteButton(tAlarm);
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

        btn_add .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertButton();
            }
        });
        return view;
    }

    //--------------------------------------------------------------------------------------------//
    // 알람 저장 : 예외 처리 필요
    //--------------------------------------------------------------------------------------------//
    public void insertButton() {
        ((MainActivity)getActivity()).setFragment(null, HomeAddFragment.newInstance());
    }

    //--------------------------------------------------------------------------------------------//
    // 알람 수정
    //--------------------------------------------------------------------------------------------//
    public void updateButton(Alarm target_alarm) {
        Bundle bundle = new Bundle();
        bundle.putString("id" , Integer.toString(target_alarm.getId()));
        MyDebug.log("UPDATE BUTTN + ID " + target_alarm.getId());
        bundle.putString("alarmDate" , target_alarm.getAlarmDate());
        bundle.putString("alarmTime" , target_alarm.getAlarmTime());
        bundle.putString("alarmNote" , target_alarm.getAlarmNote());
        bundle.putString("videoId" , target_alarm.getVideoId());
        bundle.putString("videoName", target_alarm.getVideoName());
        /*  DATA 전달 */
        ((MainActivity)getActivity()).setFragment(bundle, HomeAddFragment.newInstance());
    }

    public void deleteButton(Alarm alarm) {
        videoAlarmManager.DeleteAlarm(alarm.getId());
        ((MainActivity)getActivity()).unRegisterAlarm(alarm.getId());
    }
}
