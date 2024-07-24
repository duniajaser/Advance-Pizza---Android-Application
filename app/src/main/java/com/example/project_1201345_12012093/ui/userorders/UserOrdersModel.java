package com.example.project_1201345_12012093.ui.userorders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserOrdersModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UserOrdersModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is users fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}