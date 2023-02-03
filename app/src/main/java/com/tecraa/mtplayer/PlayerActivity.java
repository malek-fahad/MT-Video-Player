package com.tecraa.mtplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tecraa.mtplayer.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {

    ActivityPlayerBinding binding;
    Intent intent;
    int orientation,id;
    boolean screenLock;

    RecentPlay updateRecentPlay;
    RecentPlayDatabase db;
    Double videoWidth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        //basic
        screenLock = false;
        //get orientation
        orientation = this.getResources().getConfiguration().orientation;

        intent = getIntent();
        Uri videoUri = Uri.parse(intent.getStringExtra("video"));
        String videoTitle = intent.getStringExtra("title");
        videoWidth = Double.valueOf(intent.getStringExtra("width"));

        videoWidthCheck();
        binding.videoPlayerVV.setVideoURI(videoUri);
        binding.videoPlayerVV.start();
        binding.videoPlayerTitleTV.setText(videoTitle);



        //updateRecentVideoToDB


        id = 1;
        updateRecentVideoToDB(videoTitle,videoUri);



        binding.screenRotationBtn.setOnClickListener(v->{
            screenRotation();

        });
        binding.screenLockBtn.setOnClickListener(v->{
            screenLock();

        });
        binding.screenUnlockBtn.setOnClickListener(v->{
            screenUnlock();

        });

        binding.videoPlayPauseBtn.setOnClickListener(v->{
            videoPlayPause();
        });

        binding.backBtn.setOnClickListener(v->{
            finish();
        });

        binding.videoScreenBtn.setOnClickListener(v->{
            videoPlayerWidthSetup();
        });

        binding.videoBackwardBtn.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(PlayerActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    if (screenLock!=true){
                        binding.videoPlayerVV.seekTo(binding.videoPlayerVV.getCurrentPosition()-10*1000);
                        binding.videoBackwardBtn.setText("10s");
                        new Handler().postDelayed(()->binding.videoBackwardBtn.setText(""),1000);
                    }else {
                        Toast.makeText(getApplicationContext(), "Scree Lock", Toast.LENGTH_SHORT).show();
                    }
                    return super.onDoubleTap(motionEvent);
                }
                @Override
                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

                    return false;
                }
            });

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        binding.videoForwardBtn.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(PlayerActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    if (screenLock!=true){
                        binding.videoPlayerVV.seekTo(binding.videoPlayerVV.getCurrentPosition()+10*1000);
                        binding.videoForwardBtn.setText("10s");
                        new Handler().postDelayed(()->binding.videoForwardBtn.setText(""),1000);
                    }else {
                        Toast.makeText(getApplicationContext(), "Scree Lock", Toast.LENGTH_SHORT).show();
                    }
                    return super.onDoubleTap(motionEvent);
                }
                @Override
                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

                    return false;
                }
            });

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });






    }

    private void videoWidthCheck() {
        if (videoWidth>=1200){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    private void updateRecentVideoToDB(String videoTitle, Uri videoUri) {


        //saveRecentVideoToDB
//        RecentPlay recentPlay = new RecentPlay();
//        recentPlay.setVideoTitle(videoTitle);
//        recentPlay.setVideoUri(String.valueOf(videoUri));
//        RecentPlayDatabase.getRecentPlayDatabase(getApplicationContext()).getDao().insertRecentPlay(recentPlay);

        updateRecentPlay = new RecentPlay();

        updateRecentPlay.setId(id);
        updateRecentPlay.setVideoTitle(videoTitle);
        updateRecentPlay.setVideoUri(String.valueOf(videoUri));
        RecentPlayDatabase.getRecentPlayDatabase(getApplicationContext()).getDao().updateRecentPlay(updateRecentPlay);

    }


    private void videoPlayerWidthSetup() {
        ///video player size width. video width



    }

    @Override
    public void onBackPressed() {
       if (binding.screenUnlockBtn.getVisibility() == View.VISIBLE){
           Toast.makeText(getApplicationContext(), "Video Player scree is Lock", Toast.LENGTH_SHORT).show();
       }else {
           finish();
       }
    }

    private void videoPlayPause() {
        if (binding.videoPlayerVV.isPlaying()){
            binding.videoPlayerVV.pause();
            binding.videoPlayPauseBtn.setBackgroundResource(R.drawable.ic_round_play_24);
        }else {
            binding.videoPlayerVV.start();
            binding.videoPlayPauseBtn.setBackgroundResource(R.drawable.ic_round_pause_24);
        }
    }

    private void screenUnlock() {
        screenLock = false;
        binding.screenUnlockBtn.setVisibility(View.GONE);
        binding.backBtn.setVisibility(View.VISIBLE);
        binding.videoPlayerTitleTV.setVisibility(View.VISIBLE);
        binding.videoPlayerOptionBtn.setVisibility(View.VISIBLE);
        binding.screenRotationBtn.setVisibility(View.VISIBLE);
        binding.screenLockBtn.setVisibility(View.VISIBLE);
        binding.videoSkipPreviousBtn.setVisibility(View.VISIBLE);
        binding.videoPlayPauseBtn.setVisibility(View.VISIBLE);
        binding.videoSkipNextBtn.setVisibility(View.VISIBLE);
        binding.videoScreenBtn.setVisibility(View.VISIBLE);
    }

    private void screenLock() {
        screenLock = true;
        binding.screenUnlockBtn.setVisibility(View.VISIBLE);
        binding.backBtn.setVisibility(View.GONE);
        binding.videoPlayerTitleTV.setVisibility(View.GONE);
        binding.videoPlayerOptionBtn.setVisibility(View.GONE);
        binding.screenRotationBtn.setVisibility(View.GONE);
        binding.screenLockBtn.setVisibility(View.GONE);
        binding.videoSkipPreviousBtn.setVisibility(View.GONE);
        binding.videoPlayPauseBtn.setVisibility(View.GONE);
        binding.videoSkipNextBtn.setVisibility(View.GONE);
        binding.videoScreenBtn.setVisibility(View.GONE);
    }

    private void screenRotation() {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }
}