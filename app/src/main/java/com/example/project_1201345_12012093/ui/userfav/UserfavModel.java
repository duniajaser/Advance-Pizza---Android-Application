package com.example.project_1201345_12012093.ui.userfav;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserfavModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UserfavModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is User fav. fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}