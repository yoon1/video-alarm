package com.example.videoalarm.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoalarm.R;
import com.example.videoalarm.models.Alarm;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class AdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Alarm> alarmList;

    public AdapterHome(Context context, List<Alarm> alarmList) {
        this.context = context;
        this.alarmList = alarmList;
    }

    class HomeHolder extends RecyclerView.ViewHolder {
        ImageView thumbanil;
        TextView dateAndTimeText;
        TextView contentText;
        TextView thumbText;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            thumbanil = itemView.findViewById(R.id.home_thumb_photo);
            dateAndTimeText = itemView.findViewById(R.id.home_date_time_txt);
            contentText = itemView.findViewById(R.id.home_content_txt);
            thumbText = itemView.findViewById(R.id.home_thumb_txt);
        }

        public void setData(Alarm alarm) {
            final String getContent = (String) alarm.getAlarmNote();
            String getDateAndTime = (String) alarm.getAlarmDate();
            String getThumbnailText = (String) alarm.getVideoName();
            //String getThumb = alarm.getSnippet().getThumbnails().getMedium().getUrl();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, getContent, Toast.LENGTH_SHORT).show();
                }
            });
            dateAndTimeText.setText(getDateAndTime);
            thumbText.setText(getThumbnailText);
        }
    }
    public void setData(Alarm data) {

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