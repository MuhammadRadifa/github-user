package com.dicoding.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.R
import com.dicoding.githubuser.helper.ViewModelFactory
import com.dicoding.githubuser.utils.SettingPreferences
import com.dicoding.githubuser.utils.dataStore


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        mainViewModel = obtainViewModel(this)
        mainViewModel.getThemeSettings().observe(
            this,
        ) {
            AppCompatDelegate.setDefaultNightMode(if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            },3000)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
}