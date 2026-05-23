package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun HomeContent(onNavigateToVideo: (String) -> Unit) {
    val context = LocalContext.current
    var filesInDir by remember { mutableStateOf(emptyList<File>()) }

    LaunchedEffect(Unit) {
        val extPath = context.getExternalFilesDir(null)
        if (extPath != null) {
            val folder = File(extPath, "FolderMe")
            if (folder.exists() && folder.isDirectory) {
                filesInDir = folder.listFiles()?.toList() ?: emptyList()
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("SlideShow Flickr (API Placeholder)", style = MaterialTheme.typography.titleMedium)
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Artikel :", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Blogger Articles will appear here based on Blogger API.")
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("YouTube Playlist :", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Mock item
                    ListItem(
                        headlineContent = { Text("Mock Video Title") },
                        supportingContent = { Text("Channel Name") },
                        leadingContent = { DefaultIcon(Icons.Filled.PlayArrow) },
                        modifier = Modifier.clickable { onNavigateToVideo("dQw4w9WgXcQ") }
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("File :", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (filesInDir.isEmpty()) {
                        Text("No files found in FolderMe")
                    } else {
                        filesInDir.forEach { file ->
                            Text("- ${file.name}", modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AboutContent() {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("About Norax Kuadrat", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Aplikasi Serbaguna dari Norax Kuadrat mengintegrasikan Flickr, Blogger, dan YouTube.")
            }
        }
    }
}

@Composable
fun GalleryContent() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Kategori File", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Apk\tImage\tEbook\nAudio\tVideo\tOther...")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Informasi Penyimpanan", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Memory Internal / SDCard info goes here.")
            }
        }
    }
}

@Composable
fun ServiceContent() {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Layanan Norax Kuadrat", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Norax Kuadrat menjalankan service background untuk sinkronisasi Flickr, Blogger, dan YouTube content.")
            }
        }
    }
}

@Composable
fun ContactContent() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:asepmo.story@gmail.com")
                }
                context.startActivity(intent)
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Kirim Email", style = MaterialTheme.typography.titleLarge)
                Text("Email ke Gmail: asepmo.story@gmail.com")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Social Media", style = MaterialTheme.typography.titleLarge)
                Text("Facebook | Twitter | YouTube")
            }
        }
    }
}

@Composable
fun DefaultIcon(imageVector: androidx.compose.ui.graphics.vector.ImageVector) {
    Icon(imageVector = imageVector, contentDescription = null)
}
