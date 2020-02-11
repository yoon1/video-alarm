package com.example.videoalarm.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoalarm.R;
import com.example.videoalarm.fragment.HomeAddFragment;
import com.example.videoalarm.fragment.HomeFragment;
import com.example.videoalarm.fragment.SearchFragment;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.utils.MyDebug;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class AdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Alarm> alarmList;

    HomeFragment fragment;

    public AdapterHome(Context context, List<Alarm> alarmList, HomeFragment fragment) {
        this.context = context;
        this.alarmList = alarmList;
        this.fragment = fragment;
    }

    class HomeHolder extends RecyclerView.ViewHolder {
        ImageView thumbanil;
        TextView dateAndTimeText;
        TextView contentText;
        TextView thumbText;
        Switch enableButton;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            thumbanil = itemView.findViewById(R.id.home_thumb_photo);
            dateAndTimeText = itemView.findViewById(R.id.home_date_time_txt);
            contentText = itemView.findViewById(R.id.home_content_txt);
            thumbText = itemView.findViewById(R.id.home_thumb_txt);
            enableButton = itemView.findViewById(R.id.enableButton);
        }

        public void setData(final Alarm alarm) {
            final Alarm curAlarm = alarm;
            final String getContent = (String) alarm.getAlarmNote();
            final String getDateAndTime = (String) alarm.getAlarmDate() + " " + alarm.getAlarmTime();
            final int getAlarmId = alarm.getId();


            String getThumbnailText = (String) alarm.getVideoName();
            //String getThumb = alarm.getSnippet().getThumbnails().getMedium().getUrl();
            String getThumb = "https://img.youtube.com/vi/" + (String) alarm.getVideoId() + "/" + "hqdefault.jpg" ;

            MyDebug.log("#####getEnable : " + alarm.getEnable() ) ;
            if( alarm.getEnable() == 0 ) enableButton.setChecked(false);
            else enableButton.setChecked(true);

            MyDebug.log("AdapterHome getVideoId : " + alarm.getVideoId());

            dateAndTimeText.setText(getDateAndTime);
            contentText.setText(getContent);
            thumbText.setText(getThumbnailText);
            Picasso.get()
                    .load(getThumb)
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(thumbanil, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Thumbnail success");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d(TAG, "Thumbnail error");
                        }
                    });

             enableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MyDebug.log("enable Button Click !! ");
                    //String isActiva;
                    if (isChecked == true) {
                        //isActiva = "0";
                        Toast.makeText(context, " false => true", Toast.LENGTH_SHORT).show();
                        MyDebug.log("ACTION_DOWN: getAlarmId + " + getAlarmId);
                        fragment.updateEnable(getAlarmId);
                        enableButton.setChecked(true);
                    } else {
                        // isActiva = "1";
                        Toast.makeText(context, " true => false ", Toast.LENGTH_SHORT).show();
                        MyDebug.log("ACTION_UP: getAlarmId + " + getAlarmId );
                        fragment.updateEnable(getAlarmId);
                        Log.d("ontouchEvent", "enableid.isChecked() : " + enableButton.isChecked());
                        enableButton.setChecked(false);
                    }
                }
            });
             /*
            enableButton.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event ) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            MyDebug.log("ACTION_DOWN: getAlarmId + " + getAlarmId);
                            fragment.updateEnable(getAlarmId);
                            notifyDataSetChanged();
                            return true;
                        case MotionEvent.ACTION_UP:
                            MyDebug.log("ACTION_UP: getAlarmId + " + getAlarmId );
                            fragment.updateEnable(getAlarmId);
                            notifyDataSetChanged();
                            Log.d("ontouchEvent", "enableid.isChecked() : " + enableButton.isChecked());
                            return false;
                    }
                    return false;
                }
            });
              */
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item_home, parent, false);
        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        HomeHolder hh = (HomeHolder) holder;
        hh.setData(alarm);
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

}