package com.example.posts.data.repository

import com.example.posts.data.db.PostDao
import com.example.posts.data.db.PostEntity
import com.example.posts.data.model.Post
import com.example.posts.data.network.PostApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val postDao: PostDao
): PostRepository {
    override suspend fun getPosts(): List<Post> = postApi.getPosts()

    override fun getFavoritePosts(): Flow<List<PostEntity>> = postDao.getFavouritePosts()

    override suspend fun addFavorite(post: PostEntity) = postDao.insertPost(post)

    override suspend fun removeFavorite(post: PostEntity) = postDao.deletePost(post)

}