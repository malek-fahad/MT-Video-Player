package com.tecraa.mtplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderViewHolder> {
    Context context;
    List<Folder> folderList;

    public FolderAdapter(Context context, List<Folder> folderList) {
        this.context = context;
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FolderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_folder_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        Folder folder = folderList.get(position);
        int totalVideo = folder.getTotalVideo();
        long folderID = folder.getFolderID();
        String folderName = folder.getFolderName();
        String fID = String.valueOf(folder.folderID);

        holder.folderNameTV.setText(folderName);
        holder.totalVideosTV.setText(totalVideo+"videos");


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra("folderID", fID);
            intent.putExtra("folderName", folderName);
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }
}
