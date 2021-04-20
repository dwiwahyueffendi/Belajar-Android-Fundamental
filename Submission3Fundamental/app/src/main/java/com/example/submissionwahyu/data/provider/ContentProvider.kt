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
        private const val AUTH = "com.example.submissionwahyu"
        private const val TABLE_NAME = "favorite_database"
       // private const val SCHEMA = "schema"

        const val ID_FAVORITE_USER = 1
        //const val FAVORITE_USER = 2
        val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTH, TABLE_NAME, ID_FAVORITE_USER)
            //sUriMatcher.addURI(AUTH, "$TABLE_NAME/#", FAVORITE_USER)
        }

        /*private val contentUri: Uri = Uri.Builder().scheme(SCHEMA)
            .authority(AUTH)
            .appendPath(TABLE_NAME)
            .build()*/
    }

    private lateinit var userQuery: QueryDatabase
    //private lateinit var cursor: Cursor

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = -1

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

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
        return when(sUriMatcher.match(uri)){
            ID_FAVORITE_USER -> userQuery.getConsumerUser()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = -1
}