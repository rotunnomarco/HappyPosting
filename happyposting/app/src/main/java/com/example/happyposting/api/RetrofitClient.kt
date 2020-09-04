package com.example.happyposting.api

import com.example.happyposting.BuildConfig
import com.example.happyposting.api.adapter.NetworkResponseAdapterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    val client: ApiService by lazy {
        // Setup retrofit
        Retrofit.Builder()
            //Set base API
            .baseUrl("https://jsonplaceholder.typicode.com")

            //Add Factory Adapter to support methods that return types different than Call, CallAdapter.Factory()
            .addCallAdapterFactory(NetworkResponseAdapterFactory())

            //Add Factory Converter for the serialization and deserialization of all types of objects
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))

            //Add OkHttpClient object as client
            .client(OkHttpClient.Builder().build())

            //Building the RetrofitClient followed by the creation of a single interface Proxy
            .build().create(ApiService::class.java)
    }
    //endregion
}