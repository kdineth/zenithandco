package com.example.zenithco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.zenithco.ui.screens.*
import com.example.zenithco.ui.theme.ZenithTheme

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZenithTheme {
                ZenithApp()
            }
        }
    }
}

@Composable
fun ZenithApp() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen(
            onSplashComplete = {
                showSplash = false
            }
        )
    } else {
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) "main" else "login"
        ) {
            composable("login") {
                LoginScreen(
                    onLogin = {
                        isLoggedIn = true
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate("register")
                    },
                    onForgotPassword = {
                        navController.navigate("forgot_password")
                    }
                )
            }

            composable("register") {
                RegisterScreen(
                    onRegister = {
                        isLoggedIn = true
                        navController.navigate("main") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            composable("forgot_password") {
                ForgetPasswordScreen(
                    onSubmit = {
                        navController.popBackStack()
                    },
                    onBackToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            composable("main") {
                ZenithNav(
                    navController = rememberNavController(),
                    onLogout = {
                        isLoggedIn = false
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ZenithNav(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    val bottomNavItems = listOf(
        BottomNavItem("home", "Home", Icons.Filled.Home),
        BottomNavItem("categories", "Categories", Icons.Filled.Info),
        BottomNavItem("favorites", "Favorites", Icons.Filled.Favorite),
        BottomNavItem("cart", "Cart", Icons.Filled.ShoppingCart)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    navController = navController,
                    items = bottomNavItems
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(
                    onProductClick = { productId ->
                        navController.navigate("product_details/$productId")
                    },
                    onLogout = onLogout,
                    onNav = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    },
                    currentRoute = currentRoute,
                    modifier = Modifier
                )
            }
            composable("categories") {
                CategoriesScreen(
                    onBack = { navController.popBackStack() },
                    onCategoryClick = { categoryName ->
                        navController.navigate("category_products/$categoryName")
                    }
                )
            }
            composable("favorites") {
                FavouritesScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable("cart") {
                CartScreen(
                    onBack = { navController.popBackStack() },
                    onProceedToPayment = { navController.navigate("payment") }
                )
            }
            composable("payment") {
                PaymentScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable("register") {
                RegisterScreen(
                    onRegister = {
                        navController.navigate("home") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack("login", inclusive = false)
                    }
                )
            }
            composable("forgot_password") {
                ForgetPasswordScreen(
                    onSubmit = {
                        navController.popBackStack("login", inclusive = false)
                    },
                    onBackToLogin = {
                        navController.popBackStack("login", inclusive = false)
                    }
                )
            }
            composable(
                "category_products/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                CategoryProductsScreen(
                    category = category,
                    onBack = { navController.popBackStack() },
                    onProductClick = { productId ->
                        navController.navigate("product_details/$productId")
                    }
                )
            }
            composable(
                "product_details/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ProductDetailsScreen(
                    productId = productId,
                    onBack = { navController.popBackStack() },
                    onBuyNow = { navController.navigate("payment") }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem>
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(28.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}