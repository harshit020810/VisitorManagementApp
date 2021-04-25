package com.example.visitormanagementapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.visitormanagementapp.Models.RoomEntityForVisit;
import com.example.visitormanagementapp.Repositories.VisitRepository;

import java.util.List;

public class SecurityViewModel extends AndroidViewModel {

    private VisitRepository visitRepository;
    private LiveData<List<RoomEntityForVisit>> listLiveData;

    public SecurityViewModel(@NonNull Application application) {
        super(application);
        visitRepository = new VisitRepository(application);
        listLiveData = visitRepository.getAllForSecurityHomeScreen();
    }

    public LiveData<List<RoomEntityForVisit>> getAllForSecurityHomeScreen(){
        return listLiveData;
    }
}
