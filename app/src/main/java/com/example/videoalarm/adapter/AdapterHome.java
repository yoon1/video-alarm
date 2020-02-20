package com.example.videoalarm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.videoalarm.R;
import com.example.videoalarm.fragment.HomeFragment;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.utils.VideoAlarmManagerUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;
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
        //TextView thumbText;
        Switch enableButton;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            thumbanil = itemView.findViewById(R.id.home_thumb_photo);
            dateAndTimeText = itemView.findViewById(R.id.home_date_time_txt);
            //contentText = itemView.findViewById(R.id.home_content_txt);
            contentText  = itemView.findViewById(R.id.home_content_txt);
            enableButton = itemView.findViewById(R.id.enableButton);
        }

        public void setData(final Alarm alarm) {
            final String getContent = (String) alarm.getAlarmNote();
            final String getDate = alarm.getPrintFormatDate();
            final String getTime = alarm.getPrintFormatTime();
            final int getAlarmId = alarm.getId();
            final String getThumbnailText = (String) alarm.getVideoName();

            String getThumb = "https://img.youtube.com/vi/" + (String) alarm.getVideoId() + "/" + "hqdefault.jpg" ;

            if( alarm.getEnable() == 0 ) enableButton.setChecked(false);
            else enableButton.setChecked(true);

            dateAndTimeText.setText(getDate + "\n" + getTime);
            contentText.setText(getContent);
            //thumbText.setText(getThumbnailText);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, getThumbnailText, Toast.LENGTH_SHORT).show();
                }
            });

            Picasso.get()
                    .load(getThumb)
                    .placeholder(R.drawable.default_video)
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
                    if (isChecked == true) {
                        //isActiva = "0";
                        Toast.makeText(context, " false => true", Toast.LENGTH_SHORT).show();
                        VideoAlarmManagerUtil.getInstance().UpdateAlarmEnable(getAlarmId);
                        enableButton.setChecked(true);
                    } else {
                        // isActiva = "1";
                        Toast.makeText(context, " true => false ", Toast.LENGTH_SHORT).show();
                        VideoAlarmManagerUtil.getInstance().UpdateAlarmEnable(getAlarmId);
                        enableButton.setChecked(false);
                    }
                }
            });
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