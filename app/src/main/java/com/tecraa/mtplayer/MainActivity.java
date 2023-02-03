package com.tecraa.mtplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tecraa.mtplayer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding  binding;

    List<Folder> folderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setTitle("Folders");


        requestAllPermission();

        binding.recentVideoBtn.setOnClickListener(v->{
            recentVideo();
        });


    }

    private void recentVideo() {

        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        //intent.putExtra("contact", contact);
        startActivity(intent);


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

        folderList = new ArrayList<>();

        String [] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_ID
        };

        Uri contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = getContentResolver().query(contentUri,projection,null,null,MediaStore.Video.Media.BUCKET_DISPLAY_NAME + " ASC");



        if (cursor!=null && !cursor.equals("")){

            cursor.moveToPosition(0);
            while (true){

                int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));

                String folderName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                long folderID = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID));

                String selection=MediaStore.Video.Media.BUCKET_DISPLAY_NAME +" like?";
                String[] selectionArgs=new String[]{String.valueOf(folderName)};

                Cursor cursor1 = getContentResolver().query(contentUri,projection,selection,selectionArgs,MediaStore.Video.Media.DISPLAY_NAME + " ASC");

                int totalVideo = cursor1.getCount();

                folderList.add(new Folder(folderID,totalVideo,folderName));

                if(!cursor.isLast()){
                    cursor.moveToNext();
                }else {
                    break;
                }
            }

        }

        FolderAdapter adapter = new FolderAdapter(MainActivity.this,folderList);
        binding.folderListRV.setAdapter(adapter);


    }
}