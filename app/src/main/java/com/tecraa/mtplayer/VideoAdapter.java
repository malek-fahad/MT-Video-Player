package com.tecraa.mtplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    Context context;
    List<Video> videoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        Video video = videoList.get(position);

        Long id = video.getId();
        Long date = video.getDate();
        String name = video.getName();
        String folder = video.getFolder();
        String duration = video.getDuration();
        Double size = video.getSize();
        Double width = video.getWidth();
        Uri uri = video.getUri();



        Glide.with(context).load(uri).into(holder.videoPreviewIV);
        holder.videoTitleTV.setText(name);
        holder.videoTimeTV.setText(duration);
        holder.newVideoTV.setText(folder);
        holder.itemView.setOnClickListener(v->{
            String uriStr = String.valueOf(uri);
            String widthStr = String.valueOf(width);
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("video", uriStr);
            intent.putExtra("title", name);
            intent.putExtra("width", widthStr);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
