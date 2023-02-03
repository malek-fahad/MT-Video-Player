package com.tecraa.mtplayer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecentPlayDAO {

    @Insert
    void insertRecentPlay(RecentPlay recentPlay);
    @Update
    void updateRecentPlay(RecentPlay recentPlay);
    @Delete
    void deleteRecentPlay(RecentPlay recentPlay);


    @Query("SELECT * FROM RecentPlay")
    List<RecentPlay> getAllRecentPlay();

//    @Query("SELECT * FROM RecentPlay WHERE id = :id")
//    RecentPlay findByUserId(String id);

}
