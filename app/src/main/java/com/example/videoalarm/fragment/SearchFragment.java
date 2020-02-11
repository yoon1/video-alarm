package com.example.videoalarm.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videoalarm.activity.MainActivity;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.models.Search.ModelVideolist;
import com.example.videoalarm.models.Search.VideoYoutube;
import com.example.videoalarm.R;
import com.example.videoalarm.adapter.AdapterSearch;
import com.example.videoalarm.network.YoutubeAPI;
import com.example.videoalarm.utils.MyDebug;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment {

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    private EditText input_query;
    private Button btn_serach;
    private AdapterSearch adapter;
    private LinearLayoutManager manager;
    private List<VideoYoutube> videoList = new ArrayList<>();
    private RecyclerView rv;
    private Alarm alarm;

    private String alarmId;
    private String videoId;
    private String alarmDate;
    private String alarmTime;
    private String alarmNote;
    private String videoName;


    public SearchFragment() {
        //Required empoty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        input_query = view.findViewById(R.id.input_query);
        btn_serach = view.findViewById(R.id.btn_search);
        rv = view.findViewById(R.id.recyclerView2);
        adapter = new AdapterSearch(getContext(), videoList, this);
        manager = new LinearLayoutManager(getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);
        /* if(videoList.size() == 0) {
            (input_query.getText().toString());
        } */

        if (getArguments() != null) {
            MyDebug.log("SEARCH : Argument존재.");
            setAlarmData();
        }
        else {
            MyDebug.log("SEARCH : Argument존재안함.");
        }

        btn_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input_query.getText().toString())) {
                    getJson(input_query.getText().toString());
                }else {
                    Toast.makeText(getContext(), "You need to enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getJson(String query) {
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.srch + YoutubeAPI.KEY +
                YoutubeAPI.maxResults + YoutubeAPI.part + YoutubeAPI.order + YoutubeAPI.query + query + YoutubeAPI.type;
        Call<ModelVideolist> data = YoutubeAPI.getSearchVideo().getYoutube(url);

        data.enqueue(new Callback<ModelVideolist>() {
            @Override
            public void onResponse(Call<ModelVideolist> call, Response<ModelVideolist> response) {
                if( response.errorBody() != null) {
                    Log.w(TAG, "onResponse search : " + response.errorBody().toString() );
                } else {
                    ModelVideolist mv = response.body();
                    if( mv.getItems().size() != 0) {
                        videoList.clear(); // reload
                        videoList.addAll(mv.getItems());
                        adapter.notifyDataSetChanged();;
                    } else {
                        Toast.makeText(getContext(), "No video", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelVideolist> call, Throwable t) {
                Log.e(TAG, "onFailure search : " , t);
            }
        });
    }

    public void selectVideo(String videoId, String videoName) {
        /* 선택하면 video Id를 반환할 예정 */
        MyDebug.log("--------- SearchFragment ----------");
        MyDebug.log("---------반환할 번들 출력----------");
        Bundle bundle = new Bundle();

        // 매우 에러나던거 try/catch처리해주니깐 괜찮.... */
        try {
            if (alarmId!= null && !alarmId.isEmpty())
                bundle.putString("alarmId", alarmId);
            if ( alarmDate != null && !alarmDate.isEmpty() )
                bundle.putString("alarmDate", alarmDate);
            if ( alarmTime != null && !alarmTime.isEmpty() )
                bundle.putString("alarmTime", alarmTime);
            if ( alarmNote!= null && !alarmNote.isEmpty() )
                bundle.putString("alarmNote", alarmNote);
        } catch(NullPointerException e) {
            MyDebug.log("NULL ERROR : ", e.toString());
        }
        /* videoId, Name도 null처리 해야되는데 쫌따하자. */
        bundle.putString("videoId", videoId);
        bundle.putString("videoName", videoName);
        MyDebug.log("나는 이 비디오를 넘길게 " + videoId);
        MyDebug.log("나는 이 비디오를 넘길게 이름은 이거 " + videoName);
        MyDebug.log("---------반환할 번들 출력----------");
        /*  DATA 전달 */

        MyDebug.log("#SEARCH 시작시작=====>>> ");
        MyDebug.log("AlarmDate "+ alarmDate);
        MyDebug.log("AlarmTime "+ alarmTime);
        MyDebug.log("AlarmNote"+ alarmNote);
        MyDebug.log("VideoId "+ videoId);
        MyDebug.log("VideoName"+ videoName);
        MyDebug.log("AlarmId "+ alarmId);
        MyDebug.log("#SEARCH 종료종료=====>>> ");

        ((MainActivity)getActivity()).setFragment(bundle, HomeAddFragment.newInstance());
    }

    public void playVideo(String videoId) {
         MyDebug.log("Fragment진입 : " + videoId );
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v="+ videoId));
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }

    private void setAlarmData() {
        MyDebug.log("--------- SearchFragment ----------");

        //if ( Integer.toString(getArguments().getInt("alarmId")) != null && !Integer.toString(getArguments().getInt("alarmId")).isEmpty())
        try {
            if (getArguments().getString("alarmId") != null && !getArguments().getString("alarmId").isEmpty()) {
                MyDebug.log("alarmId : " + getArguments().getString("alarmId"));
                alarmId  = getArguments().getString("alarmId");
            }
            if (getArguments().getString("videoId") != null && !getArguments().getString("videoId").isEmpty()) {
                MyDebug.log("videoId : " + getArguments().getString("videoId"));
                videoId = (getArguments().getString("videoId"));
            }
            if (getArguments().getString("alarmDate") != null && !getArguments().getString("alarmDate").isEmpty()) {
                MyDebug.log("alarmDate : " + getArguments().getString("alarmDate"));
                alarmDate = getArguments().getString("alarmDate");

            }
            if (getArguments().getString("alarmTime") != null && !getArguments().getString("alarmTime").isEmpty()){
                MyDebug.log("alarmTime : " + getArguments().getString("alarmTime"));
                alarmTime = (getArguments().getString("alarmTime"));
            }
            if (getArguments().getString("alarmNote") != null && !getArguments().getString("alarmNote").isEmpty()) {
                MyDebug.log("alarmNote : " + getArguments().getString("alarmNote"));
                alarmNote = getArguments().getString("alarmNote");
            }
            if (getArguments().getString("videoName") != null && !getArguments().getString("videoName").isEmpty()) {
                MyDebug.log("videoName : " + getArguments().getString("videoName"));
                videoName = getArguments().getString("videoName");
            }

            MyDebug.log("-------------------------------");

        } catch (NullPointerException e) {
            MyDebug.log("NULL ERROR : ", e.toString());
        }
        MyDebug.log("---------가져온 번들 출력----------");
    }
}