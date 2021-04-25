package com.example.visitormanagementapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.visitormanagementapp.Interfaces.RoomDao;
import com.example.visitormanagementapp.Interfaces.RoomDaoForVisit;
import com.example.visitormanagementapp.Models.RoomEntityForExtraInfo;
import com.example.visitormanagementapp.Models.RoomEntityForVisit;

@Database(entities = {RoomEntityForExtraInfo.class, RoomEntityForVisit.class}, version = 1)
public abstract class DatabaseRoomForExtra extends RoomDatabase {

    public abstract RoomDao roomDao();
    public abstract RoomDaoForVisit roomDaoForVisit();


    private static DatabaseRoomForExtra databaseRoomForExtra;

    public static synchronized DatabaseRoomForExtra getDatabaseRoom(Context context){

        if(databaseRoomForExtra == null){
            databaseRoomForExtra = Room.databaseBuilder(context, DatabaseRoomForExtra.class, "App Data Final 5")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databaseRoomForExtra;
    }

}
