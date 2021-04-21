package com.example.submissionwahyu.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionwahyu.R
import com.example.submissionwahyu.api.RetrofitConfig
import com.example.submissionwahyu.data.endpoint.UserResponse
import com.example.submissionwahyu.data.endpoint.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(search: String, context: Context) {
        RetrofitConfig.getUser()
            .getSearchUsers(search)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, userResponse: Response<UserResponse>) {
                    val body = userResponse.body()?.items
                    listUsers.postValue(body)
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(context, R.string.connection_failed, Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}