package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToVideo: (String) -> Unit
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Norax Navigation", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                Divider()
                
                val drawerItems = listOf(
                    Triple("Home", Icons.Default.Home, Screen.Home.route),
                    Triple("About", Icons.Default.Info, Screen.About.route),
                    Triple("Gallery", Icons.Default.Image, Screen.Gallery.route),
                    Triple("Service", Icons.Default.Build, Screen.Service.route),
                    Triple("Contact", Icons.Default.Email, Screen.Contact.route),
                    Triple("Settings", Icons.Default.Settings, Screen.Settings.route)
                )
                
                drawerItems.forEach { (title, icon, route) ->
                    NavigationDrawerItem(
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(title) },
                        selected = currentRoute == route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentRoute.replaceFirstChar { it.uppercase() }) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Browser logic */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Browser")
                        }
                        IconButton(onClick = { /* Download logic */ }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Download") // placeholder
                        }
                        IconButton(onClick = { /* YouTube logic */ }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "YouTube") 
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) { HomeContent(onNavigateToVideo) }
                    composable(Screen.About.route) { AboutContent() }
                    composable(Screen.Gallery.route) { GalleryContent() }
                    composable(Screen.Service.route) { ServiceContent() }
                    composable(Screen.Contact.route) { ContactContent() }
                    composable(Screen.Settings.route) { SettingsContent() }
                }
            }
        }
    }
}
