package com.example.project_1201345_12012093.ui.logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LogoutModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is logout fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}