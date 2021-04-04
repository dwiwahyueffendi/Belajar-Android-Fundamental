package com.example.notesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.notesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.notesapp.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

class NoteHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        //Proses Menginisiasi Database
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {                //Membuka Koneksi Database
        database = dataBaseHelper.writableDatabase
    }

    fun close() {               //Menutup Koneksi Database
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {        //Proses Mengambil data
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {     //Mengambil data dengan ID
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {      //Menyimpan data
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {       //Mengupdate data
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {           //Menghapus data
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}