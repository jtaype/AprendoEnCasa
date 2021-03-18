package com.example.aprendoencasa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aprendoencasa.Model.Item;
import com.example.aprendoencasa.Model.VideoDetalle;
import com.example.aprendoencasa.R;
import com.example.aprendoencasa.Video;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.jar.Attributes;

import javax.xml.namespace.QName;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoDetalleAdapter extends RecyclerView.Adapter<VideoDetalleAdapter.VideoDetalleViewHolder>{
    private Context context;
    private List<Item> videoDetalleList;
    private String converted_date;

    public VideoDetalleAdapter(Context context, List<Item> videoDetalleList) {
        this.context = context;
        this.videoDetalleList = videoDetalleList;
    }

    @NonNull
    @Override
    public VideoDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new VideoDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoDetalleViewHolder holder, int position) {
        // holder.publishedAt.setText(videoDetalleList.get(position).getSnippet().getPublishedAt());
        holder.publishedAt.setText(setUpDateTime(videoDetalleList.get(position).getSnippet().getPublishedAt()));
        holder.description.setText(videoDetalleList.get(position).getSnippet().getDescription());
        holder.title.setText(videoDetalleList.get(position).getSnippet().getTitle());

        Glide.with(context)
                .load(videoDetalleList
                        .get(position)
                        .getSnippet().getThumbnails().getMedium().getUrl())
                .into(holder.thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Video.class);
                intent.putExtra("videoID", videoDetalleList.get(position).getId().getVideoId());
                context.startActivity(intent);
            }
        });
    }

    private String setUpDateTime(String publishedAt) {
        try {
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date date=dateFormat.parse(publishedAt);
            converted_date=format.format(date);
        }
        catch (ParseException exception){
            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
        }
        return converted_date;
    }

    @Override
    public int getItemCount() {
        return videoDetalleList.size();
    }

    public class VideoDetalleViewHolder extends RecyclerView.ViewHolder{


        private TextView publishedAt, title, description;
        //private ImageView thumbnail;
        private CircleImageView thumbnail;

        public VideoDetalleViewHolder(View itemView){
            super(itemView);

            publishedAt=itemView.findViewById(R.id.publishedAt);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            thumbnail=itemView.findViewById(R.id.thumbnail);
        }
    }



}
