package com.example.project1.ui.mytask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyTaskViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My Task");

    }

    public LiveData<String> getText() {
        return mText;
    }
}