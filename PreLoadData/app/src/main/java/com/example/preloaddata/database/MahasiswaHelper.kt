package com.example.preloaddata.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.example.preloaddata.database.DatabaseContract.MahasiswaColumns.Companion.NAMA
import com.example.preloaddata.database.DatabaseContract.MahasiswaColumns.Companion.NIM
import com.example.preloaddata.database.DatabaseContract.TABLE_NAME
import com.example.preloaddata.model.MahasiswaModel
import java.sql.SQLException

class MahasiswaHelper(context: Context) {

    private val dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private var INSTANCE: MahasiswaHelper? = null

        fun getInstance(context: Context): MahasiswaHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MahasiswaHelper(context)
                    }
                }
            }
            return INSTANCE as MahasiswaHelper
        }
    }

    //Membuka Koneksi database
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    //Menutup Koneksi database
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    /**
     * Gunakan method ini untuk mendapatkan semua data mahasiswa
     *
     * @return hasil query mahasiswa model di dalam arraylist
     */
    //Membaca
    fun getAllData(): ArrayList<MahasiswaModel> {
        val cursor = database.query(TABLE_NAME, null, null, null, null, null, "$_ID ASC", null)
        cursor.moveToFirst()
        val arrayList = ArrayList<MahasiswaModel>()
        var mahasiswaModel: MahasiswaModel
        if (cursor.count > 0) {
            do {
                mahasiswaModel = MahasiswaModel()
                mahasiswaModel.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                mahasiswaModel.name = cursor.getString(cursor.getColumnIndexOrThrow(NAMA))
                mahasiswaModel.nim = cursor.getString(cursor.getColumnIndexOrThrow(NIM))
                arrayList.add(mahasiswaModel)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    /**
     * Gunakan method ini untuk query insert
     *
     * @param mahasiswaModel inputan model mahasiswa
     * @return id dari data mahasiswa yang baru saja dimasukkan
     */
    //Menulis
    fun insert(mahasiswaModel: MahasiswaModel): Long {
        val initialValues = ContentValues()
        initialValues.put(NAMA, mahasiswaModel.name)
        initialValues.put(NIM, mahasiswaModel.nim)
        return database.insert(TABLE_NAME, null, initialValues)
    }

    /**
     * Gunakan method ini untuk cari NIM berdasarkan nama mahasiswa
     *
     * @param nama nama yang dicari
     * @return NIM dari mahasiswa
     */
    //Searching nama
    fun getDataByName(nama: String): ArrayList<MahasiswaModel> {
        val cursor = database.query(TABLE_NAME, null, "$NAMA LIKE ?", arrayOf(nama), null, null, "$_ID ASC", null)
        cursor.moveToFirst()
        val arrayList = ArrayList<MahasiswaModel>()
        var mahasiswaModel: MahasiswaModel
        if (cursor.count > 0) {
            do {
                mahasiswaModel = MahasiswaModel()
                mahasiswaModel.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                mahasiswaModel.name = cursor.getString(cursor.getColumnIndexOrThrow(NAMA))
                mahasiswaModel.nim = cursor.getString(cursor.getColumnIndexOrThrow(NIM))
                arrayList.add(mahasiswaModel)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    //Mengubah standar query-nya menjadi transactional query

    /**
     * Gunakan method ini untuk memulai sesi query transaction
     */
    fun beginTransaction() {
        database.beginTransaction()
    }

    /**
     * Gunakan method ini jika query transaction berhasil, jika error jangan panggil method ini
     */
    fun setTransactionSuccess() {
        database.setTransactionSuccessful()
    }

    /**
     * Gunakan method ini untuk mengakhiri sesi query transaction
     */
    fun endTransaction() {
        database.endTransaction()
    }

    /**
     * Gunakan method ini untuk query insert di dalam transaction
     *
     * @param mahasiswaModel inputan model mahasiswa
     */
    fun insertTransaction(mahasiswaModel: MahasiswaModel) {
        val sql = ("INSERT INTO $TABLE_NAME ($NAMA, $NIM) VALUES (?, ?)")
        val stmt = database.compileStatement(sql)
        stmt.bindString(1, mahasiswaModel.name)
        stmt.bindString(2, mahasiswaModel.nim)
        stmt.execute()
        stmt.clearBindings()
    }
}