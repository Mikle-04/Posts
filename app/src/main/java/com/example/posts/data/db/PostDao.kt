package com.example.posts.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getFavoritePosts(): List<PostEntity>

    @Insert
    suspend fun insertPost(post: PostEntity)

    @Delete
    suspend fun deletePost(post: PostEntity)


}