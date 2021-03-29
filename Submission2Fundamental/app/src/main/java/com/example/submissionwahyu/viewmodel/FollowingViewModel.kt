package com.example.submissionwahyu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionwahyu.api.RetrofitConfig
import com.example.submissionwahyu.data.User
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
                    if (response.isSuccessful){
                        listUserFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return listUserFollowing
    }
}