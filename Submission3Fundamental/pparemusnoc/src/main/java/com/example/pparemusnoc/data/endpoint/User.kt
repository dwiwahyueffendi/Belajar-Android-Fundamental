package com.example.pparemusnoc.data.endpoint

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val id: Int,
    val photoUser: String
): Parcelable