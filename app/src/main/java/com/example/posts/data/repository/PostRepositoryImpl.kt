package com.example.posts.data.repository

import android.util.Log
import com.example.posts.data.db.PostDao
import com.example.posts.data.db.PostEntity
import com.example.posts.data.db.toPost
import com.example.posts.data.db.toPostEntity
import com.example.posts.data.model.Post
import com.example.posts.data.network.PostApi
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val dao: PostDao
) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return postApi.getPosts().map { it.toPost() } // Предполагается, что PostApi возвращает PostDto
    }

    override fun getFavoritePosts(): List<Post> {
        return dao.getFavoritePosts().map { it.toPost() } // Преобразуем PostEntity в Post
    }

    override suspend fun addFavorite(post: Post) {
        if (!dao.isPostFavorite(post.id)) {
            val entity = PostEntity(id = post.id, title = post.title, body = post.body)
            dao.insertPost(entity)
            Log.d("PostRepository", "Added post ${post.id} to favorites")
        } else {
            Log.d("PostRepository", "Post ${post.id} already in favorites")
        }
    }

    override suspend fun removeFavorite(post: Post) {
        dao.deletePost(post.id)
        Log.d("PostRepository", "Removed post ${post.id} from favorites")
    }

}
