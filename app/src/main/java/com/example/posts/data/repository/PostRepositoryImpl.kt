package com.example.posts.data.repository

import com.example.posts.data.db.PostDao
import com.example.posts.data.db.toPost
import com.example.posts.data.db.toPostEntity
import com.example.posts.data.model.Post
import com.example.posts.data.network.PostApi
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val postDao: PostDao
) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return postApi.getPosts().map { it.toPost() } // Предполагается, что PostApi возвращает PostDto
    }

    override fun getFavoritePosts(): List<Post> {
        return postDao.getFavoritePosts().map { it.toPost() } // Преобразуем PostEntity в Post
    }

    override suspend fun addFavorite(post: Post) {
        postDao.insertPost(post.toPostEntity()) // Преобразуем Post в PostEntity
    }

    override suspend fun removeFavorite(post: Post) {
        postDao.insertPost(post.toPostEntity()) // Преобразуем Post в PostEntity
    }

}
