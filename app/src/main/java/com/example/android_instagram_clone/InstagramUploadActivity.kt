package com.example.android_instagram_clone

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.android_instagram_clone.databinding.ActivityInstagramUploadBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class InstagramUploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstagramUploadBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInstagramUploadBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val uri: Uri = it.data!!.data!!
                    filePath = getImageFilePath(uri)
                }
            }

        binding.uploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            getResult.launch(intent)
        }

        binding.btnUpload.setOnClickListener {
            uploadPost()
        }

        binding.footerMenu1.setOnClickListener {
            startActivity(Intent(this, InstagramPostListActivity::class.java))
        }
        binding.footerMenu2.setOnClickListener {
            startActivity(Intent(this, InstagramMyPostListActivity::class.java))
        }
        binding.footerMenu4.setOnClickListener {
            startActivity(Intent(this, InstagramUserInfoActivity::class.java))
        }
    }

    private fun getImageFilePath(contentUri: Uri): String {
        var columnIndex = 0
        val projection = arrayOf("_data")
        val cursor = contentResolver.query(contentUri, projection, null, null, null)

        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow("_data")
        }
        val result = cursor.getString(columnIndex).toString()
        cursor.close()
        return result
    }

    private fun uploadPost() {
        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("image", file.name, fileRequestBody)
        val content = RequestBody.create(MediaType.parse("text/plain"), getContent())

        (application as MasterApplication).service.uploadPost(
            part, content
        ).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    Log.d("EUM", post!!.content.toString())
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("EUM", t.message.toString())
            }
        })
    }

    fun getContent(): String {
        return binding.uploadText.text.toString()
    }
}