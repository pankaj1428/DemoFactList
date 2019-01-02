package com.sample.demofactlist;

import com.sample.demofactlist.Model.FactList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {

    @GET(".")
    Call<FactList> getData();
}
