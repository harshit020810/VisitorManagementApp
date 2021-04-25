package com.example.visitormanagementapp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.visitormanagementapp.Database.DatabaseRoomForExtra;
import com.example.visitormanagementapp.Interfaces.RoomDaoForVisit;
import com.example.visitormanagementapp.Models.RoomEntityForVisit;

import java.util.List;

public class VisitRepository {

    private RoomDaoForVisit roomDaoForVisit;
    private LiveData<List<RoomEntityForVisit>> listLiveData;

    public VisitRepository(Application application){
        DatabaseRoomForExtra databaseRoomForExtra = DatabaseRoomForExtra.getDatabaseRoom(application);
        this.roomDaoForVisit = databaseRoomForExtra.roomDaoForVisit();
        this.listLiveData = roomDaoForVisit.getAllForSecurityHomeScreen();
    }

    public LiveData<List<RoomEntityForVisit>> getAllForSecurityHomeScreen(){
        return listLiveData;
    }



}
