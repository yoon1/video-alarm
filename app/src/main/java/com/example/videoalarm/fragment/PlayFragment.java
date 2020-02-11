// NO USE

package com.example.videoalarm.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.videoalarm.R;

import androidx.fragment.app.Fragment;
import retrofit2.http.Url;

public class PlayFragment extends Fragment {
    // the fragment initialization parameters.
    private static final String VIDEO_ID= "videoId";

    private String videoId;

    VideoView videoView;
    MediaController mediaController;
    String videoURL = "https://www.youtube.com/watch?v=OBY1jDXF8QE&t=626s";

    public static PlayFragment newInstance() {
        return new PlayFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        videoView = view.findViewById(R.id.videoView); // 비디오 뷰 아이디 연결.
        mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        Uri uri = Uri.parse(videoURL);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri); // video view 의 주소를 설정.
        videoView.start();

        return view;
    }
}