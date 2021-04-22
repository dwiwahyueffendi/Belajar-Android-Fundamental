package com.example.submissionwahyu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submissionwahyu.data.database.FavoriteDatabase
import com.example.submissionwahyu.data.database.UserDatabase
import com.example.submissionwahyu.repository.FavoriteRepository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    val viewFavorite: LiveData<List<FavoriteDatabase>>?

    init {
        val userDao = UserDatabase.getUserDatabase(application)?.queryDatabase()
        viewFavorite = FavoriteRepository.getFavoriteRepo(userDao)
    }
}