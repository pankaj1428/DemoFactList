package com.sample.demofactlist;

public class ApiUtils {

    private static  String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl//facts.json/";

    public static ApiServices getAPIService() {

        return RetrofitClient.getRetrofit(BASE_URL).create(ApiServices.class);
    }
}
