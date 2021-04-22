package com.example.submissionwahyu.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionwahyu.R
import com.example.submissionwahyu.api.RetrofitConfig
import com.example.submissionwahyu.data.endpoint.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listUserFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username: String){
        RetrofitConfig.getUser()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {

                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    val body = response.body()
                    listUserFollowing.postValue(body)
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    //Toast.makeText(context, R.string.connection_failed, Toast.LENGTH_SHORT).show()
                    t.message?.let { Log.d(R.string.connection_failed.toString(), it) }
                }
            })
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return listUserFollowing
    }
}