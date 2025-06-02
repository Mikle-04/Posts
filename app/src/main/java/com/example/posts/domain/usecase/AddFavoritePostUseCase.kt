package com.example.posts.domain.usecase

import com.example.posts.data.db.PostEntity
import com.example.posts.data.repository.PostRepository
import javax.inject.Inject

class AddFavoritePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(post: PostEntity) = repository.addFavorite(post)
}