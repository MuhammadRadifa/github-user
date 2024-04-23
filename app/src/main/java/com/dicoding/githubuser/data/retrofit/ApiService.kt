package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.UserDetailResponse
import com.dicoding.githubuser.data.response.UserListResponse
import com.dicoding.githubuser.database.UserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ):Call<UserListResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserEntity>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserEntity>>
}