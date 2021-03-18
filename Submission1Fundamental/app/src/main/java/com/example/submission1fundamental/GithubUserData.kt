package com.example.submission1fundamental

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUserData(
    var Username: String,
    var Name: String,
    var Location: String,
    var Repository: String,
    var Company: String,
    var Follower: String,
    var Following: String,
    var Avatar: Int
) : Parcelable