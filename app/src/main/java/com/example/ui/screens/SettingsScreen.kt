package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.data.SettingsManager
import kotlinx.coroutines.launch

@Composable
fun SettingsContent() {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }
    val scope = rememberCoroutineScope()

    val isDark by settingsManager.isDarkModeFlow.collectAsState(initial = true)
    val flickrKey by settingsManager.flickrApiFlow.collectAsState(initial = "")
    val bloggerKey by settingsManager.bloggerApiFlow.collectAsState(initial = "")
    val youtubeKey by settingsManager.youtubeApiFlow.collectAsState(initial = "")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Theme Setting", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark Theme")
            Switch(checked = isDark, onCheckedChange = { 
                scope.launch { settingsManager.setDarkMode(it) }
            })
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Flickr API", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = flickrKey,
            onValueChange = { scope.launch { settingsManager.setFlickrApiKey(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Blogger API", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = bloggerKey,
            onValueChange = { scope.launch { settingsManager.setBloggerApiKey(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        Text("YouTube API", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = youtubeKey,
            onValueChange = { scope.launch { settingsManager.setYoutubeApiKey(it) } },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
