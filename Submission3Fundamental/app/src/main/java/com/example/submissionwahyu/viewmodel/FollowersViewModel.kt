package com.example.submissionwahyu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionwahyu.R
import com.example.submissionwahyu.api.RetrofitConfig
import com.example.submissionwahyu.data.endpoint.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    val listUserFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String){
        RetrofitConfig.getUser()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    /*if (response.isSuccessful){
                        listUserFollowers.postValue(response.body())
                    }*/
                    val body = response.body()
                    listUserFollowers.postValue(body)
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d(R.string.connection_failed.toString(), it) }
                    //Toast.makeText(context, R.string.connection_failed, Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>> {
        return listUserFollowers
    }
}