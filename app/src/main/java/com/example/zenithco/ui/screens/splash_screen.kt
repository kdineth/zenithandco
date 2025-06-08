package com.example.zenithco.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenithco.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    // Animation states
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    // Start animation and navigate after delay
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2500) // Show splash for 2.5 seconds
        onSplashComplete()
    }

    // Splash Screen Content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alphaAnim.value)
                .scale(scaleAnim.value)
        ) {
            // Logo with animation
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Zenith & Co Logo",
                modifier = Modifier
                    .size(120.dp)
                    .scale(scaleAnim.value),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App Name
            Text(
                text = "ZENITH & CO",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Timeless Luxury",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp
                ),
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Loading indicator
            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.alpha(alpha)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                Color.White,
                                shape = MaterialTheme.shapes.small
                            )
                    )
                    if (index < 2) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }

        // Bottom text
        Text(
            text = "© 2024 Zenith & Co. All rights reserved.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}