package com.example.aprendoencasa;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aprendoencasa.Model.videostats.VideoStats;
import com.example.aprendoencasa.Retrofit.GetDataService;
import com.example.aprendoencasa.Retrofit.RetrofitInstance;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Video extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    Bundle bundle;
    String videoID;
    YouTubePlayerView playerView;
    TextView views;
    private YouTubePlayer mPlayer;
    private Handler mHandler = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        bundle= new Bundle();
        playerView= findViewById(R.id.playerview);
        views=findViewById(R.id.views);


        bundle = getIntent().getExtras();
        if (bundle!=null){
            videoID= bundle.getString("videoID");
            Log.e("YOUTUBE VIDEO ID ", videoID);
        }else
            Log.e("VIDEO ID NULL", videoID.concat(" "));

        playerView.initialize("AIzaSyAzbnZQcxL1FGRT-V3vWSCCes4PGWZCYDY",this);


        getStats();

    }


    private void getStats() {
        GetDataService dataService= RetrofitInstance.getRetrofit().create(GetDataService.class);
        Call<VideoStats> videoStatsRequest =dataService
                .getVideStats("statistics","AIzaSyAzbnZQcxL1FGRT-V3vWSCCes4PGWZCYDY" ,videoID);

        videoStatsRequest.enqueue(new Callback<VideoStats>() {
            @Override
            public void onResponse(Call<VideoStats> call, Response<VideoStats> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        views.setText(response.body().getItems().get(0).getStatistics().getViewCount());
                    }else{
                        Log.e("vacioooo","null");
                    }
                }else{
                    Log.e("vacioooo22222",String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<VideoStats> call, Throwable t) {
                Toast.makeText(Video.this, "espere porfa se está cargado vistas", Toast.LENGTH_LONG).show();
                }
        });

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (null == youTubePlayer) return;
        mPlayer = youTubePlayer;


        Log.e("inicializa", videoID);
        //controles https://developers.google.com/youtube/android/player/reference/com/google/android/youtube/player/YouTubePlayer.PlayerStyle
       youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);



       // youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

       // youTubePlayer.setFullscreenControlFlags(0);
        //youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        //youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
        youTubePlayer.loadVideo(videoID);
        //youTubePlayer.cueVideo(videoID);
        //youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.e("byeee", videoID);
        Toast.makeText(this,"Something went wrong: Please try again", Toast.LENGTH_LONG).show();
    }
}