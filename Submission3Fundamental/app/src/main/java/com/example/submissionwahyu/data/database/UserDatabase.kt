package com.example.submissionwahyu.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteDatabase::class],
    version = 1
)

abstract class UserDatabase: RoomDatabase() {
    companion object{
        var CHECK_DATABASE : UserDatabase? = null

        fun getUserDatabase(context: Context): UserDatabase?{
            if (CHECK_DATABASE == null){
                synchronized(UserDatabase::class){
                    CHECK_DATABASE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database").build()
                }
            }
            return CHECK_DATABASE
        }
    }

    abstract fun queryDatabase(): QueryDatabase
}