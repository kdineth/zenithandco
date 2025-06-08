package com.example.zenithco.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zenithco.data.model.FavoritesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    onBack: () -> Unit
) {
    val favorites = FavoritesManager.favoriteProducts

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorites yet.")
            }
        } else {
            Column(modifier = Modifier.padding(padding)) {
                favorites.forEach { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(product.imageRes),
                                contentDescription = product.name,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(64.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                            ) {
                                Text(product.name, style = MaterialTheme.typography.titleMedium)
                                Text(product.description, style = MaterialTheme.typography.bodySmall)
                                Text(product.price, style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}
