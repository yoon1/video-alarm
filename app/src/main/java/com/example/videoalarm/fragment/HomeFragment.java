package com.example.videoalarm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.videoalarm.R;
import com.example.videoalarm.activity.MainActivity;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    Button btn_add;

    public static HomeFragment newInstance() {
        return new HomeFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);;
        btn_add = view.findViewById(R.id.addButton);

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
