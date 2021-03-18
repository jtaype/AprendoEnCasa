package com.example.aprendoencasa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.aprendoencasa.Adapter.VideoDetalleAdapter;
import com.example.aprendoencasa.Model.Item;
import com.example.aprendoencasa.Model.VideoDetalle;
import com.example.aprendoencasa.Retrofit.GetDataService;
import com.example.aprendoencasa.Retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoDetalleAdapter videoDetalleAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        swipeRefresh=findViewById(R.id.swipeRefresh);
        setUpRefreshListener();
        getData();
    }

    private void setUpRefreshListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                getData();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void getData() {
        swipeRefresh.setRefreshing(true);
        GetDataService dataService= RetrofitInstance.getRetrofit().create(GetDataService.class);
        Call<VideoDetalle> videoDetailsRequest =dataService.getVideoData("snippet", 50,"UCyYHc7Sfu0YPAQBn_UqZ0qw", "AIzaSyAzbnZQcxL1FGRT-V3vWSCCes4PGWZCYDY","date");

        videoDetailsRequest.enqueue(new Callback<VideoDetalle> (){
            @Override
            public void onResponse(Call<VideoDetalle> call, Response<VideoDetalle> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Log.e(TAG, "Response Successfull");
                        //Toast.makeText(MainActivity.this, "Loading, Please wair", Toast.LENGTH_LONG).show();
                        setUpRecyclerView(response.body().getItems());
                        swipeRefresh.setRefreshing(false);
                    }else{
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }else{
                    swipeRefresh.setRefreshing(true);
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VideoDetalle> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG.concat(" API REQUEST FAILED"), t.getMessage());
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void setUpRecyclerView(List<Item> items) {
        videoDetalleAdapter=new VideoDetalleAdapter(MainActivity.this,items);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoDetalleAdapter);

    }
}
