package com.tecraa.mtplayer;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

public class Video {

    Long id,date;
    String name,folder,duration;
    Double size,width;
    Uri uri;

    public Video(Long id, Long date, String name, String folder, String duration, Double size, Double width, Uri uri) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.folder = folder;
        this.duration = duration;
        this.size = size;
        this.width = width;
        this.uri = uri;
    }

    public Long getId() {
        return id;
    }

    public Long getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getFolder() {
        return folder;
    }

    public String getDuration() {
        return duration;
    }

    public Double getSize() {
        return size;
    }

    public Double getWidth() {
        return width;
    }

    public Uri getUri() {
        return uri;
    }
}
