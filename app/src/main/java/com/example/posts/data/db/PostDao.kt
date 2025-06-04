package com.example.posts.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getFavoritePosts(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post: PostEntity)

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun deletePost(postId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM posts WHERE id = :postId)")
    suspend fun isPostFavorite(postId: Int): Boolean


}