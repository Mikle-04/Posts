package com.example.posts.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.posts.data.model.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String
)
// Преобразование PostEntity в Post
fun PostEntity.toPost() = Post(
    id = id,
    title = title,
    body = body
)

// Преобразование Post в PostEntity
fun Post.toPostEntity() = PostEntity(
    id = id,
    title = title,
    body = body
)