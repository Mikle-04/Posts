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
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Посты", style = MaterialTheme.typography.headlineSmall) },
                actions = {
                    IconButton(onClick = onNavigateToFavorites) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Избранное",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> LoadingScreen()
                uiState.error != null -> ErrorScreen(errorMessage = uiState.error)
                else -> PostList(
                    posts = uiState.posts,
                    favourites = uiState.favourites,
                    onFavoriteClick = { post ->
                        if (uiState.favourites.any { it.id == post.id }) {
                            viewModel.removeFavorite(post)
                        } else {
                            viewModel.addToFavorites(post)
                        }
                    }
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