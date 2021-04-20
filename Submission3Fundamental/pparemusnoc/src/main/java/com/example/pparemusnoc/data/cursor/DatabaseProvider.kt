package com.example.pparemusnoc.data.endpoint.cursor

import android.net.Uri
import android.provider.BaseColumns

object DatabaseProvider {
    internal class FavoriteColumns: BaseColumns{
        companion object{
            const val SCHEMA = "schema"
            const val AUTH = "com.example.submissionwahyu"
            const val TABLE_NAME = "favorite_database"
            const val USERNAME = "username"
            const val ID = "id"
            const val PHOTO_USER = "photoUser"

            val CONTENT_URI = Uri.Builder().scheme(SCHEMA)
                .authority(AUTH)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}