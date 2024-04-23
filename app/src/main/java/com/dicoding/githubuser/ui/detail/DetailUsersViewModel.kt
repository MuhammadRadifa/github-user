package com.dicoding.githubuser.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.UserDetailResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsersViewModel: ViewModel(){

    private var _userDetail = MutableLiveData<UserDetailResponse?>()
    val userDetail: MutableLiveData<UserDetailResponse?> = _userDetail

    private var _isLoading = MutableLiveData<Boolean>(true)
    val isLoading:MutableLiveData<Boolean> = _isLoading

    fun getUserDetail(username:String){
        Log.i(ContentValues.TAG, "getUserDetail: $username")
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetail.value = responseBody
                        Log.d(ContentValues.TAG, "onResponse: $responseBody")
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}