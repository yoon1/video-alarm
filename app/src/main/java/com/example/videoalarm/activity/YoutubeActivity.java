package com.example.videoalarm.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.videoalarm.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public final String API_KEY = getString(R.string.api_key);
    public final String PlayList_ID = getString(R.string.playlist_id); /* google login 필요 */

    private Button youtubeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_youtube);


        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        youTubePlayerView .initialize(API_KEY, this);
        youtubeButton.findViewById(R.id.youtubeButton);

        youtubeButton.setOnClickListener(buttonClick);

    }


    //--------------------------------------------------------------------------------------------//
    // set Button : Music and Vibe
    //--------------------------------------------------------------------------------------------//
    Button.OnClickListener buttonClick = new View.OnClickListener() {
        public void onClick(View v) {
            //youtubeView.initialize(API_KEY, this); // 아까 사용한 API key를 그대로 사용하면 된다.
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
            player.cuePlaylist(PlayList_ID);
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