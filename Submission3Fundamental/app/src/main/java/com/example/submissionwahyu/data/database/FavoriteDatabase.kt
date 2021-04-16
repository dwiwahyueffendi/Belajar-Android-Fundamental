package com.example.submissionwahyu.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "favorite_database")
data class FavoriteDatabase(
    @SerializedName("login")
    val username: String,
    @PrimaryKey
    val id: Int,
    @SerializedName("avatar_url")
    val photoUser: String
): Serializable
