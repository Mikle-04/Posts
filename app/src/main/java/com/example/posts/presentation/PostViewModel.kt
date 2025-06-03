package com.example.posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posts.data.db.PostEntity
import com.example.posts.data.model.Post
import com.example.posts.domain.usecase.AddFavoritePostUseCase
import com.example.posts.domain.usecase.GetFavoritePostsUseCase
import com.example.posts.domain.usecase.GetPostsUseCase
import com.example.posts.domain.usecase.RemoveFavoritePostUseCase
import com.example.posts.presentation.models.PostUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getFavoritePostsUseCase: GetFavoritePostsUseCase,
    private val addFavoritePostUseCase: AddFavoritePostUseCase,
    private val removeFavoritePostUseCase: RemoveFavoritePostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val posts = getPostsUseCase()
                _uiState.value = _uiState.value.copy(posts = posts, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    private fun loadFavourite() {
        viewModelScope.launch {
            getFavoritePostsUseCase().collect { favorites ->
                _uiState.value = _uiState.value.copy(favourites = favorites)
            }
        }
    }

    fun addFavorite(post: Post) {
        viewModelScope.launch {
            addFavoritePostUseCase(PostEntity(post.id, post.title, post.body))
        }
    }

    fun removeFavorite(post: PostEntity) {
        viewModelScope.launch {
            removeFavoritePostUseCase(post)
        }
    }
}

