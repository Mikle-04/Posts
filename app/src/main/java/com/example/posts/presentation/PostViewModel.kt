package com.example.posts.presentation

import android.util.Log
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
import kotlinx.coroutines.flow.update
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
            try {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                val posts = repository.getPosts()
                _uiState.update {
                    it.copy(posts = posts, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }
    }

     fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(favourites = repository.getFavoritePosts())
            }
        }
    }

    fun addToFavorites(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(post)
            _uiState.update {
                it.copy(favourites = repository.getFavoritePosts())
            }
        }
    }

    fun removeFavorite(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavorite(post)
            _uiState.update {
                it.copy(favourites = repository.getFavoritePosts())
            }
        }
    }
}


