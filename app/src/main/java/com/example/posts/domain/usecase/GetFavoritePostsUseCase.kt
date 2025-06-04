package com.example.posts.domain.usecase

import com.example.posts.data.db.PostEntity
import com.example.posts.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritePostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
  //  operator fun invoke(): Flow<List<PostEntity>> = repository.getFavoritePosts()
}