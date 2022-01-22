package com.example.android_instagram_clone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.android_instagram_clone.databinding.ActivityEmailSignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailSignupBinding

    lateinit var userName: EditText
    lateinit var userPassword: EditText
    lateinit var userPasswordConfirm: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailSignupBinding.inflate(layoutInflater)

        if((application as MasterApplication).checkIsLogin()){
            finish()
            startActivity(Intent(this, InstagramPostListActivity::class.java))
        }

        setContentView(binding.root)
        initView()
        setupListener()
    }

    private fun initView() {
        userName = binding.inputId
        userPassword = binding.inputPassword
        userPasswordConfirm = binding.inputPasswordConfirm
    }

    private fun setupListener() {
        binding.btnSignUp.setOnClickListener {
            signUp()
        }
        binding.btnSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        startActivity(
            Intent(this, EmailSignInActivity::class.java)
        )
    }

    private fun signUp() {
        val username = getUserName()
        val password = getUserPassword()
        val passwordConfirm = getUserPasswordConfirm()

        (application as MasterApplication).service.signUp(
            username, password, passwordConfirm
        )
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EmailSignUpActivity, "가입에 성공하였습니다.", Toast.LENGTH_LONG)
                            .show()
                        val user = response.body()
                        val token = user?.token
                        if (token != null) {
                            saveUserToken(token)
                        }
                        (application as MasterApplication).createRetrofit()
                        this@EmailSignUpActivity.startActivity(
                            Intent(this@EmailSignUpActivity, InstagramPostListActivity::class.java)
                        )
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@EmailSignUpActivity, "가입에 실패하였습니다.", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun saveUserToken(token: String) {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.apply()
    }

    private fun getUserName(): String {
        return binding.inputId.text.toString()
    }

    private fun getUserPassword(): String {
        return binding.inputPassword.text.toString()
    }

    private fun getUserPasswordConfirm(): String {
        return binding.inputPasswordConfirm.text.toString()
    }
}
