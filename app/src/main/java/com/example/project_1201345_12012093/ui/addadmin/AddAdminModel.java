package com.example.project_1201345_12012093.ui.addadmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddAdminModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddAdminModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add admin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}