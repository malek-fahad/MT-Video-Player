package com.tecraa.mtplayer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RecentPlay.class}, version = 1)
public abstract class RecentPlayDatabase extends RoomDatabase {

    public abstract RecentPlayDAO getDao();

    static RecentPlayDatabase recentPlayDatabase = null;

    public static RecentPlayDatabase getRecentPlayDatabase(Context context){


        if (recentPlayDatabase==null){
            recentPlayDatabase = Room.databaseBuilder(
                    context,
                    RecentPlayDatabase.class,
                    "recentPlayDatabase")
                    .allowMainThreadQueries().build();



            //saveRecentVideoToDB
            RecentPlay recentPlay = new RecentPlay();
            recentPlay.setVideoTitle("");
            recentPlay.setVideoUri("");
            RecentPlayDatabase.getRecentPlayDatabase(context).getDao().insertRecentPlay(recentPlay);
        }

        return recentPlayDatabase;
    }



}
