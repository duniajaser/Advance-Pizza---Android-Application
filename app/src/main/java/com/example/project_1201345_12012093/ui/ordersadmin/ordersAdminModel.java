package com.example.project_1201345_12012093.ui.ordersadmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ordersAdminModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ordersAdminModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is orders admin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}