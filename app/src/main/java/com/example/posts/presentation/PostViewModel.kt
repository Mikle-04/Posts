package com.example.posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posts.data.model.Post
import com.example.posts.data.repository.PostRepository
import com.example.posts.presentation.models.PostUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
        loadFavorites()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val posts = repository.getPosts()
                _uiState.value = _uiState.value.copy(posts = posts, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favorites = repository.getFavoritePosts() // Предполагается, что возвращает List<Post>
                _uiState.value = _uiState.value.copy(favourites = favorites)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun addToFavorites(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(post)
            loadFavorites()
        }
    }

    fun removeFavorite(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavorite(post)
            loadFavorites()
        }
    }
}

