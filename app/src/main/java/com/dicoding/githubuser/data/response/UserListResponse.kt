package com.dicoding.githubuser.data.response

import com.dicoding.githubuser.database.UserEntity
import com.google.gson.annotations.SerializedName

data class UserListResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<UserEntity>
)

