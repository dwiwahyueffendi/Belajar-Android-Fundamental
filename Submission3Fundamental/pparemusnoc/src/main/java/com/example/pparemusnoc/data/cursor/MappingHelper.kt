package com.example.pparemusnoc.data.endpoint.cursor

import android.database.Cursor
import com.example.pparemusnoc.data.endpoint.User

object MappingHelper {
    fun cursorToArrayList(cursor: Cursor):ArrayList<User>{
        val listUser = ArrayList<User>()
        while (cursor.moveToNext()){
            val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseProvider.FavoriteColumns.USERNAME))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseProvider.FavoriteColumns.ID))
            val photoUser = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseProvider.FavoriteColumns.PHOTO_USER))
            listUser.add(User(username, id, photoUser))
        }

        return listUser
    }
}