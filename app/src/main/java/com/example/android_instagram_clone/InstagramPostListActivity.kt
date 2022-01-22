package com.example.android_instagram_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager


import com.example.android_instagram_clone.databinding.ActivityInstagramPostListBinding
import com.example.android_instagram_clone.databinding.ItemRecyclerInstagramContentViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstagramPostListActivity : AppCompatActivity() {
    lateinit var glide: RequestManager
    lateinit var binding: ActivityInstagramPostListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstagramPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        glide = Glide.with(this)


        (application as MasterApplication).service.getAllPosts().enqueue(
            object : Callback<ArrayList<Post>> {
                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if (response.isSuccessful) {
                        val postList = response.body()
                        val adapter = postList?.let { _ -> postList
                            PostAdapter(
                                postList,
                                glide,
                            )
                        }
                        binding.postRecyclerview.adapter = adapter
                        binding.postRecyclerview.layoutManager =
                            LinearLayoutManager(this@InstagramPostListActivity,
                                LinearLayoutManager.VERTICAL, false)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
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

class PostAdapter(
    var postList: ArrayList<Post>,
    var glide: RequestManager
):  RecyclerView.Adapter<PostAdapter.PostHolder>() {

    inner class PostHolder(binding: ItemRecyclerInstagramContentViewBinding) :
        RecyclerView.ViewHolder(binding.root){

        val postOwner: TextView = binding.postOwner
        val postContent: TextView = binding.postContent
        val postImage: ImageView = binding.postImage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ItemRecyclerInstagramContentViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.postOwner.text = postList[position].owner
        holder.postContent.text = postList[position].content
        glide.load(postList[position].image).into(holder.postImage)
    }

    override fun getItemCount(): Int = postList.size
}

