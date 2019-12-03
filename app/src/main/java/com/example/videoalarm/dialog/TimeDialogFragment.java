package com.example.videoalarm.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.videoalarm.R;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class TimeDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle svaedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT
                ,this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        TextView tvTitle = new TextView(getActivity());
        tvTitle.setText("TimePickerDialog Title");
        tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
        tvTitle.setPadding(5, 3, 5, 3);
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        tpd.setCustomTitle(tvTitle);

        return tpd;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        TextView tv = (TextView) getActivity().findViewById(R.id.timeText);
        tv.setText(String.format("%02d%02d", hourOfDay, minute)+"00");
    }
}
