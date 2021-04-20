package com.example.submissionwahyu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionwahyu.data.database.FavoriteDatabase
import com.example.submissionwahyu.data.database.QueryDatabase
import com.example.submissionwahyu.data.database.UserDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*@HiltViewModel
class FavoriteViewModel @Inject constructor(private var repository: FavoriteRepository): ViewModel() {
    fun getFavoriteUser(): LiveData<List<FavoriteDatabase>> =  repository.getFavoriteUser()
}*/

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userQuery: QueryDatabase?
    private var userDatabase: UserDatabase?

    //UserDatabase.getUserDatabase.queryDatabase.getFavoriteUser(

    init {
        userDatabase = UserDatabase.getUserDatabase(application)
        userQuery = userDatabase?.queryDatabase()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteDatabase>>?{
        return userQuery?.getFavoriteUser()
    }
}