/*package com.example.submissionwahyu.viewmodel

import androidx.lifecycle.LiveData
import com.example.submissionwahyu.data.database.FavoriteDatabase
import com.example.submissionwahyu.data.database.UserDatabase
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private var userDatabase: UserDatabase) {
    /*private var userQuery: QueryDatabase?
    //private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getUserDatabase(application)
        userQuery = userDatabase?.queryDatabase()
    }*/

    fun getFavoriteUser(): LiveData<List<FavoriteDatabase>> = userDatabase.queryDatabase().getFavoriteUser()
}*/