package com.example.posts.data.db

import com.example.posts.data.model.Post

data class PostDto(
    val id: Int,
    val title: String,
    val body: String
)

// Преобразование PostDto в Post
fun PostDto.toPost() = Post(
    id = id,
    title = title,
    body = body
)