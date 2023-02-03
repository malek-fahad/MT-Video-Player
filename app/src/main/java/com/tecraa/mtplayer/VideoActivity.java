package com.tecraa.mtplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tecraa.mtplayer.databinding.ActivityVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    ActivityVideoBinding binding;

    List<Video> videoList;
    Intent intent;
    int folderID;
    String folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();
        folderID = Integer.parseInt(intent.getStringExtra("folderID"));
        folderName = intent.getStringExtra("folderName");

        setTitle(folderName.replace("_"," "));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestAllPermission();






    }

    //action bar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void requestAllPermission() {

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        getData();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

    }

    private void getData() {

        videoList = new ArrayList<>();
        String [] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.WIDTH
        };

        Uri contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;


        //videos from specific folder
        String selection=MediaStore.Video.Media.BUCKET_DISPLAY_NAME +" like?";
        String[] selectionArgs=new String[]{String.valueOf(folderName)};

        Cursor cursor = getContentResolver().query(contentUri,projection,selection,selectionArgs,MediaStore.Video.Media.DISPLAY_NAME + " ASC");

        if (cursor!=null && !cursor.equals("")){

            cursor.moveToPosition(0);
            while (true){

                Long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                Double size = cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                Long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));
                Double width = cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH));




                Uri videoUri = ContentUris.withAppendedId(contentUri,id);


                videoList.add(new Video(id,date,name,folder,duration,size,width,videoUri));

                VideoAdapter adapter = new VideoAdapter(VideoActivity.this,videoList);
                binding.videoListRV.setAdapter(adapter);



                if(!cursor.isLast()){
                    cursor.moveToNext();
                }else {
                    break;
                }
            }

        }


    }
}