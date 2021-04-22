package com.example.submissionwahyu.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.submissionwahyu.R
import com.example.submissionwahyu.api.RetrofitConfig
import com.example.submissionwahyu.data.database.FavoriteDatabase
import com.example.submissionwahyu.data.database.QueryDatabase
import com.example.submissionwahyu.data.database.UserDatabase
import com.example.submissionwahyu.data.endpoint.DetailUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val listDetailUsers = MutableLiveData<DetailUser>()
    private val userQuery: QueryDatabase? = UserDatabase.getUserDatabase(application)?.queryDatabase()


    //private var userQuery: QueryDatabase = UserDatabase.getUserDatabase(application)?.queryDatabase()

    /*private var userQuery: QueryDatabase?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getUserDatabase(application)
        userQuery = userDatabase?.queryDatabase()
    }*/

    fun setUserDetail(username: String, context: Context) {
        RetrofitConfig.getUser()
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUser> {
                override fun onResponse(
                    call: Call<DetailUser>,
                    response: Response<DetailUser>
                ) {
                    if (response.isSuccessful) {
                        listDetailUsers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    Toast.makeText(context, R.string.connection_failed, Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUser> {
        return listDetailUsers
    }

    suspend fun checkFavoriteUser(id: Int) = userQuery?.checkFavoriteUser(id)

    /*fun addFavoriteUser(username: String, id: Int, photoUser: String){
        CoroutineScope(Dispatchers.IO).launch {
            var favorite = FavoriteDatabase(username, id, photoUser)
            userQuery?.addFavoriteUser(favorite)
        }
    }

    fun deleteFavoriteUser(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userQuery?.deleteFavoriteUser(id)
        }
    }*/

    /*fun checkFavoriteUser(id: Int) = viewModelScope.launch {
        userQuery?.checkFavoriteUser(id)
    }*/

    fun addFavoriteUser(username: String, id: Int, photoUser: String) = viewModelScope.launch {
        var favorite = FavoriteDatabase(username, id, photoUser)
        userQuery?.addFavoriteUser(favorite)
    }

    fun deleteFavoriteUser(id: Int) = viewModelScope.launch {
        userQuery?.deleteFavoriteUser(id)
    }

}