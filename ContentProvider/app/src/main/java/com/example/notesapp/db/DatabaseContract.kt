package com.example.notesapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    //Deklarasi content uri
    const val AUTHORITY = "com.example.notesapp"
    const val SCHEME = "content"

    internal class NoteColumns: BaseColumns{
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"

            // untuk membuat URI content://ccom.example.notesapp/note
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}