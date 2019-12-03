package com.example.videoalarm.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.videoalarm.R;
import com.example.videoalarm.utils.MyDebug;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle svaedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK,this, year, month, dayOfMonth);

        TextView tvTitle = new TextView(getActivity());
        tvTitle.setText("DatePickerDialog Title");
        tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
        tvTitle.setPadding(5, 3, 5, 3);
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        dpd.setCustomTitle(tvTitle);

        return dpd;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        TextView tv = (TextView) getActivity().findViewById(R.id.dateText);

        MyDebug.log("YEAR" + String.valueOf(year));
        MyDebug.log("MONTH" + String.valueOf(month));
        MyDebug.log("DAY" + String.valueOf(dayOfMonth));

        tv.setText(String.format("%04d%02d%02d", year, month + 1, dayOfMonth));
    }
}
