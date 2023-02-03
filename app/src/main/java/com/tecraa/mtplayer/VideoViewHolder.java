package com.tecraa.mtplayer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    ImageView videoPreviewIV;
    TextView newVideoTV, videoTimeTV, videoTitleTV;


    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoPreviewIV = itemView.findViewById(R.id.videoPreviewIV);
        newVideoTV = itemView.findViewById(R.id.newVideoTV);
        videoTimeTV = itemView.findViewById(R.id.videoTimeTV);
        videoTitleTV = itemView.findViewById(R.id.videoTitleTV);


    }
}
