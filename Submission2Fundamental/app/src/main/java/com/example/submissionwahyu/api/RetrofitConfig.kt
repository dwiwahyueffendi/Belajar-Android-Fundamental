package com.example.submissionwahyu.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {

    companion object{
        fun getUser() : ApiClient {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiInstance = retrofit.create(ApiClient::class.java)
            return apiInstance
        }
    }
}