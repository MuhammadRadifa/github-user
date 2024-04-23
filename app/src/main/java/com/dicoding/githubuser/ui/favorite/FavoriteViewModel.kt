package com.dicoding.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.repository.UserRepository

class FavoriteViewModel(application: Application = Application()):ViewModel(){
    private val userRepository:UserRepository = UserRepository(application)

    fun addFavoriteUser(user:UserEntity){
        userRepository.addFavoriteUser(user)
    }

    fun deleteFavoriteUser(user:UserEntity){
        userRepository.deleteFavoriteUser(user)
    }

    fun getAllFavoriteUsers() = userRepository.getAllFavoriteUsers()

    fun getFavoriteUser(id:Int) = userRepository.getFavoriteUser(id)
}