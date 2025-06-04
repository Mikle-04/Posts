package com.example.posts.presentation.models

import com.example.posts.data.db.PostEntity
import com.example.posts.data.model.Post

data class PostUiState(
    val posts : List<Post> = emptyList(),
    val favourites: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

