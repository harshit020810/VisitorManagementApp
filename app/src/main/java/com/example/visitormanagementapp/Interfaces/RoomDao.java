package com.example.visitormanagementapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.visitormanagementapp.Models.RoomEntityForExtraInfo;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(RoomEntityForExtraInfo roomEntityForExtraInfo);

    @Delete
    void deleteAll(RoomEntityForExtraInfo roomEntityForExtraInfo);

    @Query("SELECT * FROM VMS_DETAILS")
    List<RoomEntityForExtraInfo> getAll();

    @Query("SELECT identificationNumber FROM VMS_DETAILS WHERE name = :name AND department = :department")
    List<String> getId(String name, String department);
}
