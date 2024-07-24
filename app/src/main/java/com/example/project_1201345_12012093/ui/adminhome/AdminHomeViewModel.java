package com.example.project_1201345_12012093.ui.adminhome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminHomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdminHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is admin home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}