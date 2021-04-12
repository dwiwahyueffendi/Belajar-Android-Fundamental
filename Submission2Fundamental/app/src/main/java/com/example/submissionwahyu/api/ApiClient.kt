package com.example.submissionwahyu.api

import com.example.submissionwahyu.BuildConfig
import com.example.submissionwahyu.data.DetailUser
import com.example.submissionwahyu.data.User
import com.example.submissionwahyu.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiClient {

    @GET("search/users")
    @Headers(BuildConfig.API_KEY)
    fun getSearchUsers(@Query("q") search: String) : Call<UserResponse>

    @GET("users/{username}")
    @Headers(BuildConfig.API_KEY)
    fun getUserDetail(@Path("username") username: String) : Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers(BuildConfig.API_KEY)
    fun getFollowers(@Path("username") username: String) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers(BuildConfig.API_KEY)
    fun getFollowing(@Path("username") username: String) : Call<ArrayList<User>>
}