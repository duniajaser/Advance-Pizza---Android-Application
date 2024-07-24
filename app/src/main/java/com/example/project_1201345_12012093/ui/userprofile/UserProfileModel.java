package com.example.project_1201345_12012093.ui.userprofile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserProfileModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UserProfileModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is user profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}