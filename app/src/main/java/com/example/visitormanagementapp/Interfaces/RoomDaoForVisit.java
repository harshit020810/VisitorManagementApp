package com.example.visitormanagementapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.visitormanagementapp.Models.RoomEntityForVisit;

import java.util.List;

@Dao
public interface RoomDaoForVisit {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllForVisit(RoomEntityForVisit roomEntityForVisit);

    @Delete
    void deleteAllForVisit(RoomEntityForVisit roomEntityForVisit);

    @Query("SELECT * FROM Visit")
    List<RoomEntityForVisit> getAllForVisit();

    @Query("SELECT id, appointment, visitorName, companyName, host, department, isActive, state FROM VISIT")
    LiveData<List<RoomEntityForVisit>> getAllForSecurityHomeScreen();

}
