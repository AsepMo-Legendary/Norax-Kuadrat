package com.example.navigation

sealed class Screen(val route: String) {
    object Story : Screen("story")
    object HomeDrawer : Screen("home_drawer")
    object Home : Screen("home")
    object About : Screen("about")
    object Gallery : Screen("gallery")
    object Service : Screen("service")
    object Contact : Screen("contact")
    object Settings : Screen("settings")
    object YouTubePlayer : Screen("youtube_player/{videoId}") {
        fun createRoute(videoId: String) = "youtube_player/$videoId"
    }
}
