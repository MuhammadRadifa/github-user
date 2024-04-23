package com.dicoding.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavoriteUser(user: UserEntity)

    @Delete
    fun deleteFavoriteUser(user: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getFavoriteUser(id:Int): LiveData<UserEntity>
}