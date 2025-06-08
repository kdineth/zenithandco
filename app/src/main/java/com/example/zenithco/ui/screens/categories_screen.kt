package com.example.zenithco.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zenithco.R

val categoryList = listOf(
    Pair("Wedding Rings", R.drawable.ring1),
    Pair("Chains", R.drawable.chain1),
    Pair("Rings", R.drawable.ring1),
    Pair("Necklaces", R.drawable.necklace1),
    Pair("Earrings", R.drawable.earrings1),
    Pair("Accessories", R.drawable.cufflinks1)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onBack: () -> Unit,
    onCategoryClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            categoryList.forEach { (name, image) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(70.dp)
                        .clickable { onCategoryClick(name) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(image),
                            contentDescription = name,
                            modifier = Modifier.size(46.dp)
                        )
                        Spacer(modifier = Modifier.width(18.dp))
                        Text(name, style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}
