package com.example.notesapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.notesapp.db.DatabaseContract.AUTHORITY
import com.example.notesapp.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.example.notesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.notesapp.db.NoteHelper

class NoteProvider : ContentProvider() {

    //deklarasikan variabel terlebih dahulu dan definisikan UriMatcher untuk mengecek URI yang masuk,
    //apakah bersifat all atau ada tambahan id-nya.
    companion object {
        private const val NOTE = 1
        private const val NOTE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var noteHelper: NoteHelper

        init {
            // content://com.example.notesapp/note
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE)
            // content://com.example.notesapp/note/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", NOTE_ID)
        }
    }

    override fun onCreate(): Boolean {
        noteHelper = NoteHelper.getInstance(context as Context)
        noteHelper.open()
        return true
    }

    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            NOTE -> noteHelper.queryAll()
            NOTE_ID -> noteHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (NOTE) {
            sUriMatcher.match(uri) -> noteHelper.insert(contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        val updated: Int = when (NOTE_ID) {
            sUriMatcher.match(uri) -> noteHelper.update(uri.lastPathSegment.toString(),contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (NOTE_ID) {
            sUriMatcher.match(uri) -> noteHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}