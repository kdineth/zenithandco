package com.example.zenithco.data.model

import androidx.compose.runtime.mutableStateListOf

object FavoritesManager {
    val favoriteProducts = mutableStateListOf<Product>()

    fun addFavorite(product: Product) {
        if (product !in favoriteProducts) {
            favoriteProducts.add(product)
        }
    }
    fun removeFavorite(product: Product) {
        favoriteProducts.remove(product)
    }
    fun isFavorite(product: Product): Boolean {
        return product in favoriteProducts
    }
}
