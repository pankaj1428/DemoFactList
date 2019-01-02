package com.sample.demofactlist.ViewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.sample.demofactlist.Model.FactList;

public class FactsViewModel extends ViewModel {

    private MutableLiveData<FactList> facts;
    private FactList factList;

    public void observeUsers(@NonNull LifecycleOwner owner,
                             @NonNull Observer<FactList> observer) {
        getUsers().observe(owner, observer);
    }

    private LiveData<FactList> getUsers() {
        if (facts == null) {
            facts = new MutableLiveData<>();
           // loadFacts();
        }
        return facts;
    }
}
