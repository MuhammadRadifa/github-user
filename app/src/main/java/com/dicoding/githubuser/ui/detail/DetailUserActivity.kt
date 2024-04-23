package com.dicoding.githubuser.ui.detail

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.helper.ViewModelFactory
import com.dicoding.githubuser.ui.MainViewModel
import com.dicoding.githubuser.ui.favorite.FavoriteUserActivity
import com.dicoding.githubuser.ui.favorite.FavoriteViewModel
import com.dicoding.githubuser.ui.users.UserViewModel
import com.dicoding.githubuser.utils.SectionPagerAdapter
import com.dicoding.githubuser.utils.SettingPreferences
import com.dicoding.githubuser.utils.dataStore
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val detailUsersViewModel by viewModels<DetailUsersViewModel>()
    private val userViewModel by viewModels<UserViewModel>()

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var mainViewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setCustomView(R.layout.app_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.black)));

        val username = intent.getStringExtra(EXTRA_ID)

        if (username != null) {
            detailUsersViewModel.getUserDetail(username)
            userViewModel.setUsername(username)
        }

        favoriteViewModel = obtainViewModel(this)
        mainViewModel = obtainMainViewModel(this)

        detailUsersViewModel.userDetail.observe(this){
            users ->
            binding.apply {
                if(users == null){
                    return@observe
                }

                textViewName.text = users.name
                textViewUsername.text = users.login
                Glide.with(this@DetailUserActivity)
                    .load(users.avatarUrl)
                    .into(profileImage)
                textViewDescription.text = if (users.bio == null) "" else users.bio.toString()
                shareButton.setOnClickListener {
                    val shareIntent = Intent().setAction(Intent.ACTION_SEND)
                    shareIntent.putExtra(Intent.EXTRA_TEXT,users.htmlUrl)
                    shareIntent.setType("text/plain")
                    startActivity(Intent.createChooser(shareIntent,"Bagikan Link"))
                }
                favoriteViewModel.getFavoriteUser(users.id).observe(this@DetailUserActivity){
                    data ->
                    val isFavorite = data != null
                    favoriteButton.icon = ContextCompat.getDrawable(favoriteButton.context,if(isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
                    favoriteButton.setOnClickListener {
                        val user = UserEntity(
                            users.id,
                            users.login,
                            users.avatarUrl
                        )
                        if(isFavorite) favoriteViewModel.deleteFavoriteUser(user) else favoriteViewModel.addFavoriteUser(user)
                        Toast.makeText(this@DetailUserActivity,"${if (isFavorite) "Delete" else "Add"} Favorite User",Toast.LENGTH_SHORT).show()
                    }
                }

            }
            val sectionsPagerAdapter = SectionPagerAdapter(this)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position],if(position == 0) users?.followers else users?.following )
            }.attach()
        }

        detailUsersViewModel.isLoading.observe(this){
            showLoading(it)
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainViewModel {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val darkModeButton = menu?.findItem(R.id.dark_mode)
        mainViewModel.getThemeSettings().observe(this) {
                isDarkModeActive ->
            darkModeButton?.icon = ContextCompat.getDrawable(this, if (isDarkModeActive) R.drawable.baseline_light_mode_24 else R.drawable.baseline_dark_mode_24)
            AppCompatDelegate.setDefaultNightMode(if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.favorite_page -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
            }
            R.id.dark_mode -> {
                val isDarkMode = AppCompatDelegate.getDefaultNightMode().equals(AppCompatDelegate.MODE_NIGHT_YES)
                mainViewModel.saveThemeSetting(!isDarkMode)
                AppCompatDelegate.setDefaultNightMode(if (!isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.main.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.main.visibility = View.VISIBLE
        }
    }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_ID = "extra_id"
    }

}