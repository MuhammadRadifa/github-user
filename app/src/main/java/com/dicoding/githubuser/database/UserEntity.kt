package com.dicoding.githubuser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    @Field("id")
    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String,

    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatarUrl: String
)