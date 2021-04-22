package com.example.submissionwahyu.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "favorite_database")
@Parcelize
data class FavoriteDatabase(

    @SerializedName("login")
    @ColumnInfo(name = "username")
    @field:Json(name = "username")
    val username: String,

    @PrimaryKey(autoGenerate = false)
    @field:Json(name = "id")
    val id: Int,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "photoUser")
    @field:Json(name = "photoUser")
    val photoUser: String
): Parcelable
