package com.example.videoalarm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.videoalarm.R;
import com.example.videoalarm.fragment.SearchFragment;
import com.example.videoalarm.models.Search.VideoYoutube;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import static android.content.ContentValues.TAG;

public class AdapterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<VideoYoutube> videoList;
    SearchFragment fragment;

    public AdapterSearch(Context context, List<VideoYoutube> videoList, SearchFragment fragment) {
        this.context = context;
        this.videoList = videoList;
        this.fragment = fragment;
    }

    class YoutubeHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        Button btn_play;
        public YoutubeHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.iv_search_thumb);
            title = itemView.findViewById(R.id.tv_search_title);
            btn_play = itemView.findViewById(R.id.btn_play);
        }

        public void setData(VideoYoutube data) {
            final String getTitle = data.getSnippet().getTitle();
            String getThumb = data.getSnippet().getThumbnails().getMedium().getUrl();
            final String getVideoId = data.getId().getVideoId();
            final String getVideoName = data.getSnippet().getTitle();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.selectVideo(getVideoId, getVideoName);
                }
            });
            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.playVideo(getVideoId);
                }
            });
            title.setText(getTitle);
            Picasso.get()
                    .load(getThumb)
                    .placeholder(R.drawable.default_video)
                    .fit()
                    .centerCrop()
                    .into(thumbnail, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Thumbnail success");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d(TAG, "Thumbnail error");
                        }
                    });
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item_search, parent, false);
        return new YoutubeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoYoutube videoYoutube = videoList.get(position);
        YoutubeHolder yth = (YoutubeHolder) holder;
        yth.setData(videoYoutube);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}