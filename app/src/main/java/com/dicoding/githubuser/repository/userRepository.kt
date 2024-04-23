package com.dicoding.githubuser.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.database.GithubDatabase
import com.dicoding.githubuser.database.UserDao
import com.dicoding.githubuser.database.UserEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val userDao:UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GithubDatabase.getDatabase(application)
        userDao = db.userDao()
    }

    fun addFavoriteUser(user:UserEntity){
        executorService.execute {
            userDao.addFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(user:UserEntity){
        executorService.execute {
            Log.i("UserRepository", "deleteFavoriteUser: $user")
            userDao.deleteFavoriteUser(user)
        }
    }

    fun getAllFavoriteUsers():LiveData<List<UserEntity>> = userDao.getAllFavoriteUsers()

    fun getFavoriteUser(id:Int):LiveData<UserEntity> = userDao.getFavoriteUser(id)

}