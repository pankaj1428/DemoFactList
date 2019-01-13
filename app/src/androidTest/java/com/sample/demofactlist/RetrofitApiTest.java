package com.sample.demofactlist;

import com.sample.demofactlist.Model.FactList;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.TestCase.assertTrue;

public class RetrofitApiTest {

    @Test
    public void getData_Success() {

        ApiServices apiEndpoints = ApiUtils.getAPIService();

        Call<FactList> call = apiEndpoints.getData();

        try {
            Response<FactList> response = call.execute();
            FactList authResponse = response.body();

            assertTrue(response.isSuccessful() && authResponse.getTitle() != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getData_Failure() {

        ApiServices apiEndpoints = ApiUtils.getAPIService();

        Call<FactList> call = apiEndpoints.getData();

        try {
            Response<FactList> response = call.execute();
            FactList authResponse = response.body();

            if(!response.isSuccessful()){
                Assert.assertNotEquals(200, response.code());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
