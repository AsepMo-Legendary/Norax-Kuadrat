package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.SettingsManager
import com.example.navigation.Screen
import com.example.ui.screens.MainScreen
import com.example.ui.screens.StoryScreen
import com.example.ui.screens.YouTubePlayerScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val settingsManager = remember { SettingsManager(this) }
      val isDarkTheme by settingsManager.isDarkModeFlow.collectAsState(initial = true)

      MyApplicationTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()
        
        NavHost(
            navController = navController,
            startDestination = Screen.Story.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.Story.route) {
                StoryScreen(onNavigateToHome = {
                    try {
                        if (navController.currentDestination?.route == Screen.Story.route) {
                            navController.navigate(Screen.HomeDrawer.route) {
                                popUpTo(Screen.Story.route) { inclusive = true }
                            }
                        }
                    } catch (e: Exception) {
                        // Ignore
                    }
                })
            }
            
            composable(Screen.HomeDrawer.route) {
                MainScreen(onNavigateToVideo = { videoId ->
                    navController.navigate(Screen.YouTubePlayer.createRoute(videoId))
                })
            }
            
            composable(Screen.YouTubePlayer.route) { backStackEntry ->
                val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
                YouTubePlayerScreen(
                    videoId = videoId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
      }
    }
  }
}
