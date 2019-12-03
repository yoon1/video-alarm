package com.example.videoalarm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoalarm.R;

import androidx.fragment.app.Fragment;

public class PlaylistFragment extends Fragment {

    public static PlaylistFragment newInstance() {
        return new PlaylistFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }
}
