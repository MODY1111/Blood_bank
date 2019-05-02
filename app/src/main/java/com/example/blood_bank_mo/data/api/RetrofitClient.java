package com.example.blood_bank_mo.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.converter.gson.GsonConverterFactory.create;

public class RetrofitClient {
    private static final String BASE_URL = "http://ipda3.com/blood-bank/api/v1/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd 'T' HH:mm:ssZ")
                    .create();

            final OkHttpClient.Builder builder = new OkHttpClient.Builder();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

}