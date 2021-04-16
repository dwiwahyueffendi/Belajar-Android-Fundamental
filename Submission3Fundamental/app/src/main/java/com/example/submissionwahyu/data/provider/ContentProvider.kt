package com.example.submissionwahyu.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.submissionwahyu.data.database.QueryDatabase
import com.example.submissionwahyu.data.database.UserDatabase

class ContentProvider : ContentProvider() {

    companion object{
        const val AUTH = "com.example.submissionwahyu"
        const val TABLE_NAME = "favorite_database"
        const val ID_FAVORITE_USER = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTH, TABLE_NAME, ID_FAVORITE_USER)
        }
    }

    private lateinit var userQuery: QueryDatabase

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        userQuery = context.let {
            dao -> dao?.let { UserDatabase.getUserDatabase(it)?.queryDatabase() }
        }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor: Cursor?
        when(uriMatcher.match(uri)){
            ID_FAVORITE_USER -> {
                cursor = userQuery.getConsumerUser()

                if (context != null){
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }else ->{
                cursor = null
            }
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}