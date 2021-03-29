package com.example.submissionwahyu.data

data class DetailUser(
    val login: String,
    val id: Int,
    val name: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val followers: Int,
    val following: Int
)