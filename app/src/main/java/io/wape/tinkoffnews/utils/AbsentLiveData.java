package io.wape.tinkoffnews.utils;

import android.arch.lifecycle.LiveData;

public class AbsentLiveData extends LiveData {

    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> AbsentLiveData create() {
        return new AbsentLiveData();
    }
}