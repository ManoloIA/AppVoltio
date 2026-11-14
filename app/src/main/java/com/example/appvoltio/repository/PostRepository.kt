package com.example.appvoltio.repository

import com.example.appvoltio.data.remote.RetrofitInstance
import com.example.appvoltio.model.Post

class PostRepository {

    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}