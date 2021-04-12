package com.example.submissionwahyu.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val username: String,
    val id: Int,
    @SerializedName("avatar_url")
    val photoUser: String,
)