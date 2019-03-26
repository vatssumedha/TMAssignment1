package com.tmassignment.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sumedha Vats on 26/03/19.
 */
public class Client {

    public static final String BASE_URL = "https://app.deltaapp.in";
    public static Retrofit retrofit = null;


    public static Retrofit getClient(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
