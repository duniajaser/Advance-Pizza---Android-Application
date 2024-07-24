package com.example.project_1201345_12012093.ui.pizzamenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PizzamenuModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PizzamenuModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pizza menu fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}