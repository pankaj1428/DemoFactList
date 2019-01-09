package com.sample.demofactlist.ViewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.sample.demofactlist.ApiUtils;
import com.sample.demofactlist.Model.FactList;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            loadFacts();
        }
        return facts;
    }

    private void loadFacts()
    {
        ApiUtils.getAPIService().getData().enqueue(new Callback<FactList>() {
            @Override
            public void onResponse(Call<FactList> call, Response<FactList> response) {
                factList = response.body();
                facts.setValue(factList);
            }
            @Override
            public void onFailure(Call<FactList> call, Throwable t) {
                // send null as a result , will handle at UI
                facts.setValue(null);
            }
        });
    }
}
