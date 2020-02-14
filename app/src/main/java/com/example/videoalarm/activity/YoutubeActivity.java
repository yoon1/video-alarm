package com.example.videoalarm.activity;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.videoalarm.R;
import com.example.videoalarm.utils.MyDebug;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String API_KEY;
    String PLAYLIST_ID;
    String VIDEO_ID;
    //public final String API_KEY = getString(R.string.api_key);
    //public final String PlayList_ID = getString(R.string.playlist_id); /* google login 필요 */

    Intent passedIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_youtube);
        MyDebug.log("### YoutubeActivity!!진입!! ###");
        passedIntent = getIntent();
        processCommand(passedIntent);

        Resources res = getResources();
        API_KEY = res.getString(R.string.api_key);
        PLAYLIST_ID = res.getString(R.string.playlist_id);
        //Video_ID ="T8p7wQJ8Lu4";

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        youTubePlayerView .initialize(API_KEY, this);
        Button youtubeButton = (Button)findViewById(R.id.youtubeButton);
        youtubeButton.setOnClickListener(buttonClick);
   }

    @Override
    protected void onNewIntent(Intent intent) {
        processCommand(passedIntent);
        super.onNewIntent(intent);
    }

    private void processCommand(Intent intent) {
        if (intent != null ) {
            String id = intent.getStringExtra("id");
            Toast.makeText(this, "서비습로부터 전달받은 데이터 : " + id , Toast.LENGTH_LONG).show();
        }
    }
    //--------------------------------------------------------------------------------------------//
    // set Button : Music and Vibe
    //--------------------------------------------------------------------------------------------//
    Button.OnClickListener buttonClick = new View.OnClickListener() {
        public void onClick(View v) {
            //youtubeView.initialize(API_KEY, this); // 아까 사용한 API key를 그대로 사용하면 된다.
            Toast.makeText(YoutubeActivity.this, "여기서 영상보여줄게~", Toast.LENGTH_SHORT).show();
            MyDebug.log("### YoutubeActivity!!진입!! : 여기서 영상보여줄게~###");
        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(YoutubeActivity.this, "Failured to Initialize!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        /* start buffering */
        if(!wasRestored) {
            //player.cuePlaylist(PlayList_ID);
            player.cueVideo(VIDEO_ID);
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

}