package com.example.android_instagram_clone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_instagram_clone.databinding.ActivityInstagramPostListBinding
import com.example.android_instagram_clone.databinding.ActivityInstagramUserInfoBinding

class InstagramUserInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstagramUserInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstagramUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.footerMenu1.setOnClickListener {
            startActivity(Intent(this, InstagramPostListActivity::class.java))
        }
        binding.footerMenu3.setOnClickListener {
            startActivity(Intent(this, InstagramUploadActivity::class.java))
        }
        binding.footerMenu4.setOnClickListener {
            startActivity(Intent(this, InstagramUserInfoActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            val sharedPreference = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("login_sp", "null")
            editor.apply()
            (application as MasterApplication).createRetrofit()
            finish()

            startActivity(Intent(this, EmailSignInActivity::class.java))
        }
    }
}