package com.dicoding.githubuser.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.helper.ViewModelFactory
import com.dicoding.githubuser.ui.favorite.FavoriteUserActivity
import com.dicoding.githubuser.ui.users.UserFragment
import com.dicoding.githubuser.ui.users.UserViewModel
import com.dicoding.githubuser.utils.SettingPreferences
import com.dicoding.githubuser.utils.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()


    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setCustomView(R.layout.app_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.black)))


        mainViewModel = obtainViewModel(this)


        val userFragment = UserFragment()
        userFragment.arguments = Bundle().apply {
            putString(UserFragment.ARG_SECTION, "all")
        }
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(UserFragment::class.java.simpleName)

        if (fragment !is UserFragment) {
            fragmentManager.commit {
                add(R.id.frame_container, userFragment, UserFragment::class.java.simpleName)
            }
        }



        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.textView.text = searchView.text
                    searchView.hide()
                    userViewModel.getAllUsers(searchBar.textView.text.toString())
                    searchBar.textView.text = ""
                    false
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val darkModeButton = menu?.findItem(R.id.dark_mode)
        mainViewModel.getThemeSettings().observe(this) {
            isDarkModeActive ->
            darkModeButton?.icon = ContextCompat.getDrawable(this, if (isDarkModeActive) R.drawable.baseline_light_mode_24 else R.drawable.baseline_dark_mode_24)
            darkModeButton?.contentDescription = if (isDarkModeActive) getString(R.string.dark_mode) else getString(R.string.light_mode)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_page -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
            }
            R.id.dark_mode -> {
                val isDarkMode = AppCompatDelegate.getDefaultNightMode().equals(AppCompatDelegate.MODE_NIGHT_YES)
                mainViewModel.saveThemeSetting(!isDarkMode)
                AppCompatDelegate.setDefaultNightMode(if (!isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

}