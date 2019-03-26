package com.tmassignment.remote;



import com.tmassignment.model.InformationResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sumedha Vats on 26-03-2019.
 */
public interface APIService {

    @GET("/api/v2/meta/data")
    Call<InformationResponse> getApiInformation();

}
