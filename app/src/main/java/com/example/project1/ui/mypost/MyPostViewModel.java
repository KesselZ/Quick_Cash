package com.example.project1.ui.mypost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPostViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyPostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My Post");

    }

    public LiveData<String> getText() {
        return mText;
    }
}