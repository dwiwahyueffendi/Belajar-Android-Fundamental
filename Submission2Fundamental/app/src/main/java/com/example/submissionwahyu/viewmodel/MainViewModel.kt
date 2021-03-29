package com.example.submissionwahyu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionwahyu.api.RetrofitConfig
import com.example.submissionwahyu.data.UserResponse
import com.example.submissionwahyu.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(search: String) {
        RetrofitConfig.getUser()
            .getSearchUsers(search)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, userResponse: Response<UserResponse>) {
                    if (userResponse.isSuccessful) {
                        listUsers.postValue(userResponse.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}