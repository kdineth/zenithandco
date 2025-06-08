package com.example.zenithco.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.zenithco.R
import com.example.zenithco.data.model.CartManager
import com.example.zenithco.data.model.FavoritesManager
import com.example.zenithco.data.model.productList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("home", "Home", Icons.Filled.Home),
    BottomNavItem("categories", "Categories", Icons.Filled.Info),
    BottomNavItem("favorites", "Favorites", Icons.Filled.Favorite),
    BottomNavItem("cart", "Cart", Icons.Filled.ShoppingCart)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (String) -> Unit,
    onLogout: () -> Unit,
    onNav: (String) -> Unit,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                TopAppBar(
                    title = {
                        Text(
                            "Zenith & Co",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    actions = {
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Logout",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.zenithco),
                    contentDescription = "Zenith & Co Hero",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp, bottom = 4.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Text(
                    text = "Discover timeless luxury with Zenith & Co's exclusive collection.",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                val carouselImages = listOf(R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4)
                val carouselState = rememberLazyListState()
                var carouselIndex by remember { mutableStateOf(0) }
                LaunchedEffect(carouselIndex) {
                    delay(6500)
                    val nextIndex = (carouselIndex + 1) % carouselImages.size
                    carouselIndex = nextIndex
                    coroutineScope.launch {
                        carouselState.animateScrollToItem(carouselIndex)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .padding(vertical = 12.dp)
                ) {
                    LazyRow(
                        state = carouselState,
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(carouselImages.size) { idx ->
                            Image(
                                painter = painterResource(carouselImages[idx]),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillParentMaxHeight()
                                    .aspectRatio(1.7f)
                                    .clip(MaterialTheme.shapes.medium),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Featured Products",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Swipe to see more →",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                // Scrollable Featured Products
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(productList.take(5)) { product ->  // Show 5 featured products
                        Card(
                            modifier = Modifier
                                .width(180.dp)
                                .height(280.dp)
                                .clickable { onProductClick(product.id) },
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp)
                            ) {
                                Card(
                                    modifier = Modifier.size(100.dp),
                                    shape = MaterialTheme.shapes.small,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Image(
                                        painter = painterResource(product.imageRes),
                                        contentDescription = product.name,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = product.price,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = {
                                        CartManager.addProduct(product)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("${product.name} added to cart!")
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(vertical = 8.dp)
                                ) {
                                    Text("Add to Cart", style = MaterialTheme.typography.labelSmall)
                                }
                                IconButton(
                                    onClick = {
                                        if (FavoritesManager.isFavorite(product)) {
                                            FavoritesManager.removeFavorite(product)
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("${product.name} removed from favorites!")
                                            }
                                        } else {
                                            FavoritesManager.addFavorite(product)
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("${product.name} added to favorites!")
                                            }
                                        }
                                    },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (FavoritesManager.isFavorite(product)) {
                                            Icons.Filled.Favorite
                                        } else {
                                            Icons.Outlined.FavoriteBorder
                                        },
                                        contentDescription = if (FavoritesManager.isFavorite(product)) {
                                            "Remove from Favorites"
                                        } else {
                                            "Add to Favorites"
                                        },
                                        tint = if (FavoritesManager.isFavorite(product)) {
                                            MaterialTheme.colorScheme.error
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "All Jewelry",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            items(productList) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                        .clickable { onProductClick(product.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(80.dp),
                            shape = MaterialTheme.shapes.small,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Image(
                                painter = painterResource(product.imageRes),
                                contentDescription = product.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = product.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = product.price,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    CartManager.addProduct(product)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("${product.name} added to cart!")
                                    }
                                },
                                modifier = Modifier.width(100.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Add to Cart",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (FavoritesManager.isFavorite(product)) {
                                        FavoritesManager.removeFavorite(product)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("${product.name} removed from favorites!")
                                        }
                                    } else {
                                        FavoritesManager.addFavorite(product)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("${product.name} added to favorites!")
                                        }
                                    }
                                },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = if (FavoritesManager.isFavorite(product)) {
                                        Icons.Filled.Favorite
                                    } else {
                                        Icons.Outlined.FavoriteBorder
                                    },
                                    contentDescription = if (FavoritesManager.isFavorite(product)) {
                                        "Remove from Favorites"
                                    } else {
                                        "Add to Favorites"
                                    },
                                    tint = if (FavoritesManager.isFavorite(product)) {
                                        MaterialTheme.colorScheme.error
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // SnackbarHost positioned at the bottom
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}