package com.example.submissionwahyu.data.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QueryDatabase {
    @Insert
    suspend fun addFavoriteUser(favoriteDatabase: FavoriteDatabase)

    @Query("DELETE FROM favorite_database WHERE favorite_database.id = :id")
    suspend fun deleteFavoriteUser(id: Int): Int

    @Query("SELECT * FROM favorite_database")
    fun getFavoriteUser(): LiveData<List<FavoriteDatabase>>

    @Query("SELECT count(*) FROM favorite_database WHERE favorite_database.id = :id")
    suspend fun checkFavoriteUser(id: Int): Int

    @Query("SELECT * FROM favorite_database")
    fun getConsumerUser(): Cursor
}