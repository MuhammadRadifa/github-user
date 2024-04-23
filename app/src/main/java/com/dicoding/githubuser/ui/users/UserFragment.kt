package com.dicoding.githubuser.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.databinding.FragmentUserBinding
import com.dicoding.githubuser.helper.ViewModelFactory
import com.dicoding.githubuser.ui.favorite.FavoriteViewModel
import com.dicoding.githubuser.utils.SettingPreferences
import com.dicoding.githubuser.utils.UserAdapter
import com.dicoding.githubuser.utils.dataStore


class UserFragment : Fragment() {

    companion object {
        const val ARG_SECTION = "section_arg"
    }

    private var _binding: FragmentUserBinding? = null

    private val binding get() = _binding!!

    private val userViewModel by activityViewModels<UserViewModel>()

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.userList.layoutManager = layoutManager

        val type = arguments?.getString(ARG_SECTION)

        favoriteViewModel = obtainViewModel(requireActivity())

        when(type){
            "followers" -> {
                userViewModel.getAllUsersFollowers(userViewModel.username.value.toString())
            }
            "following" -> {
                userViewModel.getAllUsersFollowing(userViewModel.username.value.toString())
            }
            "favorite"->{
                favoriteViewModel.getAllFavoriteUsers().observe(viewLifecycleOwner){
                    setUsersData(it)
                    setEmptyData(it.isEmpty())
                }
            }
            else -> {
                userViewModel.getAllUsers()
            }
        }

        if (type != "favorite"){
            userViewModel.userList.observe(viewLifecycleOwner){
                setUsersData(it)
                setEmptyData(it.isEmpty())
            }
        }

        userViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setEmptyData(isEmpty:Boolean){
        if(isEmpty){
            binding.emptyUser.root.visibility = View.VISIBLE
        }else{
            binding.emptyUser.root.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): FavoriteViewModel {
        val pref = SettingPreferences.getInstance(activity.application.dataStore)
        val factory = ViewModelFactory.getInstance(activity.application,pref)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun setUsersData(consumerUser: List<UserEntity>) {
        val adapter = UserAdapter()
        adapter.submitList(consumerUser)
        binding.userList.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}