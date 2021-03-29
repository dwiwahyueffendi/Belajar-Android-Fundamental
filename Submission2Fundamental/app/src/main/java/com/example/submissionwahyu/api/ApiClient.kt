package com.example.submissionwahyu.api

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
    @Headers("Authorization: token caf8f67493058a15d4cddcb5badb07945ec6fda4")
    fun getSearchUsers( @Query("q") search: String) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token caf8f67493058a15d4cddcb5badb07945ec6fda4")
    fun getUserDetail( @Path("username") username: String) : Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token caf8f67493058a15d4cddcb5badb07945ec6fda4")
    fun getFollowers( @Path("username") username: String) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token caf8f67493058a15d4cddcb5badb07945ec6fda4")
    fun getFollowing( @Path("username") username: String) : Call<ArrayList<User>>
}