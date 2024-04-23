package com.dicoding.githubuser.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuser.ui.users.UserFragment

class SectionPagerAdapter(activity:AppCompatActivity): FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = UserFragment()
        fragment.arguments = Bundle().apply {
            putString(UserFragment.ARG_SECTION, if(position == 0) "followers" else "following")
        }
        return fragment
    }

}