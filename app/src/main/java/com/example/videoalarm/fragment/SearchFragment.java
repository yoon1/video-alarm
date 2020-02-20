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

    private String id;
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

        if (getArguments() != null) {
            setAlarmData();
        }
        else {
            MyDebug.log("getArguments is null");
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
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.srch + YoutubeAPI.getKey((MainActivity)getActivity())+
                YoutubeAPI.maxResults + YoutubeAPI.part + YoutubeAPI.order + YoutubeAPI.query + query + YoutubeAPI.type;
        Call<ModelVideolist> data = YoutubeAPI.getSearchVideo().getYoutube(url);

        data.enqueue(new Callback<ModelVideolist>() {
            @Override
            public void onResponse(Call<ModelVideolist> call, Response<ModelVideolist> response) {
                if( response.errorBody() != null) {
                    MyDebug.log("onResponse error : " + response.errorBody().toString() );
                } else {
                    ModelVideolist mv = response.body();
                    if( mv.getItems().size() != 0) {
                        videoList.clear();
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
        Bundle bundle = new Bundle();
       try {
            if (id != null && !id.isEmpty())
                bundle.putString("id", id);
            if ( alarmDate != null && !alarmDate.isEmpty() )
                bundle.putString("alarmDate", alarmDate);
            if ( alarmTime != null && !alarmTime.isEmpty() )
                bundle.putString("alarmTime", alarmTime);
            if ( alarmNote!= null && !alarmNote.isEmpty() )
                bundle.putString("alarmNote", alarmNote);
        } catch(NullPointerException e) {
            MyDebug.log("selectVideo Error : ", e.toString());
        }
        /* TODO : videoId, Name null처리 . */
        bundle.putString("videoId", videoId);
        bundle.putString("videoName", videoName);
        ((MainActivity)getActivity()).setFragment(bundle, HomeAddFragment.newInstance());
    }

    public void playVideo(String videoId) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v="+ videoId));
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }

    private void setAlarmData() {
        try {
            if (getArguments().getString("id") != null && !getArguments().getString("id").isEmpty()) {
                id  = getArguments().getString("id");
            }
            if (getArguments().getString("videoId") != null && !getArguments().getString("videoId").isEmpty()) {
                videoId = (getArguments().getString("videoId"));
            }
            if (getArguments().getString("alarmDate") != null && !getArguments().getString("alarmDate").isEmpty()) {
                alarmDate = getArguments().getString("alarmDate");
            }
            if (getArguments().getString("alarmTime") != null && !getArguments().getString("alarmTime").isEmpty()){
                alarmTime = (getArguments().getString("alarmTime"));
            }
            if (getArguments().getString("alarmNote") != null && !getArguments().getString("alarmNote").isEmpty()) {
                alarmNote = getArguments().getString("alarmNote");
            }
            if (getArguments().getString("videoName") != null && !getArguments().getString("videoName").isEmpty()) {
                videoName = getArguments().getString("videoName");
            }
        } catch (NullPointerException e) {
            MyDebug.log( "" + e );
        }
    }
}