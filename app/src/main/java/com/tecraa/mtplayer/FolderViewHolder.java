package com.tecraa.mtplayer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FolderViewHolder extends RecyclerView.ViewHolder {

    TextView folderNameTV,totalVideosTV,totalNewVideosTV;

    public FolderViewHolder(@NonNull View itemView) {
        super(itemView);
        folderNameTV = itemView.findViewById(R.id.folderNameTV);
        totalVideosTV = itemView.findViewById(R.id.totalVideosTV);
        totalNewVideosTV = itemView.findViewById(R.id.totalNewVideosTV);
    }
}
