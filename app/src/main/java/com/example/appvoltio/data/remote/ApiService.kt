package com.example.appvoltio.data.remote

import com.example.appvoltio.model.Post
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    suspend fun getPosts(): List<Post>
}