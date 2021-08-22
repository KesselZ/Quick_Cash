package com.example.project1.ui.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Post Your Task!");

    }

    public LiveData<String> getText() {
        return mText;
    }
}