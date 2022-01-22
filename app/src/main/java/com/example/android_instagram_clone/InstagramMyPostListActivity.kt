package com.example.android_instagram_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_instagram_clone.databinding.ActivityInstagramMyPostListBinding

class InstagramMyPostListActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstagramMyPostListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstagramMyPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.footerMenu2.setOnClickListener {
            startActivity(Intent(this, InstagramMyPostListActivity::class.java))
        }
        binding.footerMenu3.setOnClickListener {
            startActivity(Intent(this, InstagramUploadActivity::class.java))
        }
        binding.footerMenu4.setOnClickListener {
            startActivity(Intent(this, InstagramUserInfoActivity::class.java))
        }
    }
}