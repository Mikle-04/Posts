package com.example.posts.domain.usecase

import com.example.posts.data.db.PostEntity
import com.example.posts.data.repository.PostRepository
import javax.inject.Inject

class RemoveFavoritePostUseCase @Inject constructor(
   private val postsRepository: PostRepository)
{
   // suspend operator fun invoke(post: PostEntity) = postsRepository.removeFavorite(post)
}