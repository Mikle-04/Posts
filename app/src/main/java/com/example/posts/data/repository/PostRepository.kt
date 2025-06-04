package com.example.posts.data.repository

import com.example.posts.data.db.PostEntity
import com.example.posts.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(): List<Post>
    fun getFavoritePosts(): List<Post>
    suspend fun addFavorite(post: Post)
    suspend fun removeFavorite(post: Post)
}