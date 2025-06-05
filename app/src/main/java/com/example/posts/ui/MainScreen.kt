package com.example.posts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.posts.data.model.Post
import com.example.posts.presentation.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToFavorites: () -> Unit,
    viewModel: PostViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val state = uiState.value

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Посты") },
                actions = {
                    IconButton(onClick = onNavigateToFavorites) {
                        Icon(Icons.Default.Favorite, contentDescription = "Избранное")
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingScreen(modifier = Modifier.padding(padding))
            }
            state.error != null -> {
                ErrorScreen(
                    errorMessage = state.error,
                    modifier = Modifier.padding(padding)
                )
            }
            else -> {
                PostList(
                    posts = state.posts,
                    favourites = state.favourites,
                    onFavoriteClick = { post ->
                        if (state.favourites.any { it.id == post.id }) {
                            viewModel.removeFavorite(post)
                        } else {
                            viewModel.addToFavorites(post)
                        }
                    },
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun PostList(
    posts: List<Post>,
    favourites: List<Post>,
    onFavoriteClick: (Post) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(posts) { post ->
            PostItem(
                post = post,
                isFavorite = favourites.any { it.id == post.id },
                onFavoriteClick = { onFavoriteClick(post) }
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(errorMessage: String?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ошибка: ${errorMessage ?: "Неизвестная ошибка"}",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}