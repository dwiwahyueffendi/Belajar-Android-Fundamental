package com.example.pparemusnoc.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pparemusnoc.data.endpoint.cursor.DatabaseProvider
import com.example.pparemusnoc.data.endpoint.cursor.MappingHelper
import com.example.pparemusnoc.data.endpoint.User

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var listModel = MutableLiveData<ArrayList<User>>()

    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseProvider.FavoriteColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val listCursorToArray = cursor?.let { MappingHelper.cursorToArrayList(it) }
        listModel.postValue(listCursorToArray)
    }

    fun getFavoriteUser(): LiveData<ArrayList<User>> {
        return listModel
    }
}