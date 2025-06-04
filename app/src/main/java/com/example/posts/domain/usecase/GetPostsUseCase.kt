package com.example.posts.domain.usecase

import com.example.posts.data.model.Post
import com.example.posts.data.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
   // suspend operator fun invoke(): List<Post> = repository.getPosts()
}