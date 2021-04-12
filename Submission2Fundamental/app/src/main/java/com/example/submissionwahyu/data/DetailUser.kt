package com.example.submissionwahyu.data

import com.google.gson.annotations.SerializedName

data class DetailUser(
    @SerializedName("login")
    val username: String,
    val id: Int,
    val name: String,
    @SerializedName("avatar_url")
    val photoUser: String,
    @SerializedName("followers_url")
    val listFollowers: String,
    @SerializedName("following_url")
    val listFollowing: String,
    val followers: Int,
    val following: Int,
    val company: String,
    val location: String
)