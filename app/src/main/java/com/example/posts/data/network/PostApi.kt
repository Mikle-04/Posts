package com.example.posts.data.network

import com.example.posts.data.model.Post
import retrofit2.http.GET

interface PostApi {

    @GET("posts")
    suspend fun getPosts(): List<Post>
}