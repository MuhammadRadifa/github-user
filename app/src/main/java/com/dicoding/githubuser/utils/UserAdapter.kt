package com.dicoding.githubuser.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.databinding.CardUserBinding
import com.dicoding.githubuser.ui.detail.DetailUserActivity


class UserAdapter:ListAdapter<UserEntity,UserAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        val binding = CardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(private val binding: CardUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity){
            binding.textViewUsername.text = user.login
            Glide.with(binding.imageView.context)
                .load(user.avatarUrl)
                .into(binding.profileImage)

            binding.root.setOnClickListener {
                val intentDetailActivity = Intent(binding.root.context,DetailUserActivity::class.java)
                intentDetailActivity.putExtra(DetailUserActivity.EXTRA_ID,user.login)
                binding.root.context.startActivity(intentDetailActivity)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}