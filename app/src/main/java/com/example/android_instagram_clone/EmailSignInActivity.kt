package com.example.android_instagram_clone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_instagram_clone.databinding.ActivityEmailSigninBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignInActivity : AppCompatActivity() {
    lateinit var binding: ActivityEmailSigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupOnClickListener()


    }

    private fun setupOnClickListener() {
        binding.btnSignUp.setOnClickListener {
            signUp()
        }

        binding.btnSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signUp() {
        val intent = Intent(this, EmailSignupActivity::class.java)
        startActivity(intent)
    }

    private fun signIn() {
        val username = binding.inputId.text.toString()
        val password = binding.inputPassword.text.toString()
        (application as MasterApplication).service.signIn(
            username, password
        ).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EmailSignInActivity, "로그인에 성공하였습니다.", Toast.LENGTH_LONG)
                        .show()
                    val user = response.body()
                    val token = user?.token
                    saveUserToken(token)
                    (application as MasterApplication).createRetrofit()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@EmailSignInActivity, "로그인에 실패하셨습니다", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun saveUserToken(token: String?) {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.apply()
    }
}
