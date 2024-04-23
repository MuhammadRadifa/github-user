package com.dicoding.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class GithubDatabase:RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object{
        @Volatile
        private var INSTANCE: GithubDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context):GithubDatabase{
            if (INSTANCE == null){
                synchronized(GithubDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GithubDatabase::class.java, "github_database")
                        .build()
                }
            }
            return INSTANCE as GithubDatabase
        }
    }
}