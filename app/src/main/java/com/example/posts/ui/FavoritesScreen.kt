package com.example.posts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.posts.data.model.Post
import com.example.posts.presentation.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onNavigateBack: () -> Unit,
    viewModel: PostViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val state = uiState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Избранное") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
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
            state.favourites.isEmpty() -> {
                EmptyFavoritesScreen(modifier = Modifier.padding(padding))
            }
            else -> {
                FavoritePostList(
                    posts = state.favourites, // Исправлено: favoritePosts вместо favourites
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun FavoritePostList(posts: List<Post>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(posts) { post ->
            PostItem(post = post, onFavoriteClick = {}) // Без действия, так как уже в избранном
        }
    }
}

@Composable
fun EmptyFavoritesScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Нет избранных постов",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}