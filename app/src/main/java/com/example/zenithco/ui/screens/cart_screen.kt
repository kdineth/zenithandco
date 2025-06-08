package com.example.zenithco.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.example.zenithco.data.model.CartManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBack: () -> Unit,
    onProceedToPayment: () -> Unit
) {
    val cart = CartManager.cartProducts
    val total = cart.sumOf { it.price.replace("$", "").toIntOrNull() ?: 0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (cart.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty.")
                }
            } else {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Group products by ID to show quantity
                    val grouped = cart.groupBy { it.id }
                    grouped.forEach { (_, products) ->
                        val product = products.first()
                        val quantity = products.size
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Image(
                                painter = painterResource(product.imageRes),
                                contentDescription = product.name,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(product.name, style = MaterialTheme.typography.titleMedium)
                                Text(product.price, style = MaterialTheme.typography.bodyMedium)
                                Text("Qty: $quantity", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Total: $${total}", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onProceedToPayment,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Proceed to Payment")
                    }
                }
            }
        }
    }
}
