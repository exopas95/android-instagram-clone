package com.example.android_instagram_clone

import android.app.Person
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("json/students/")
    fun getStudentsList(): Call<ArrayList<PersonFromServer>>

    @POST("json/students/")
    fun createStudent(
        @Body params: HashMap<String, Any>
    ): Call<PersonFromServer>

    @POST("json/students/")
    fun createStudentEasy(
        @Body params: PersonFromServer
    ): Call<PersonFromServer>

    @POST("user/signup/")
    @FormUrlEncoded
    fun signUp(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ): Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>
}
