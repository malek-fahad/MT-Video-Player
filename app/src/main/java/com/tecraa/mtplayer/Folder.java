package com.tecraa.mtplayer;

import android.net.Uri;

public class Folder {
    long folderID;
    int totalVideo;
    String folderName;

    public Folder(long folderID, int totalVideo, String folderName) {
        this.folderID = folderID;
        this.totalVideo = totalVideo;
        this.folderName = folderName;
    }

    public long getFolderID() {
        return folderID;
    }

    public void setFolderID(long folderID) {
        this.folderID = folderID;
    }

    public int getTotalVideo() {
        return totalVideo;
    }

    public void setTotalVideo(int totalVideo) {
        this.totalVideo = totalVideo;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
