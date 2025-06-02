package com.example.posts.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface PostDao {

    @Query("SELECT * FROM posts")
    fun getFavouritePosts(): Flow<List<PostEntity>>

    @Insert
    suspend fun insertPost(post: PostEntity)

    @Delete
    suspend fun deletePost(post: PostEntity)


}