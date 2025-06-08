package com.example.zenithco.data.model

import androidx.compose.runtime.mutableStateListOf


object CartManager {
    val cartProducts = mutableStateListOf<Product>()

    fun addProduct(product: Product) {
        cartProducts.add(product)
    }

    fun clearCart() {
        cartProducts.clear()
    }
}
