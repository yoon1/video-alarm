package com.example.videoalarm.network;

import android.content.Context;
import android.content.res.Resources;

import com.example.videoalarm.R;
import com.example.videoalarm.models.Search.ModelVideolist;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class YoutubeAPI {
    Context context;
    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    public final String KEY = context.getResources().getString(R.string.api_key);

    public static final String maxResults = "&maxResults=10";
    public static final String part = "&part=snippet";
    public static final String order = "&order=date";
    public static final String type = "&type=video";

    public static final String srch = "search?";
    public static final String query = "&q=";



    public interface SearchVideo {
        @GET
        Call<ModelVideolist> getYoutube(@Url String url);
    }

    private static SearchVideo searchVideo = null;

    public static SearchVideo getSearchVideo() {
        if ( searchVideo == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            searchVideo = retrofit.create(SearchVideo.class);
        }
        return searchVideo;
    }
}
