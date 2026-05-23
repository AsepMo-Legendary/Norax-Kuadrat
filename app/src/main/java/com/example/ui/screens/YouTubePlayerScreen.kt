package com.example.ui.screens

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubePlayerScreen(videoId: String, onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("YouTube Player") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        AndroidView(
            modifier = Modifier.fillMaxSize().padding(padding),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient()
                    
                    val iframeHtml = """
                        <html>
                            <body style="margin:0;padding:0;">
                                <iframe width="100%" height="100%" src="https://www.youtube.com/embed/$videoId?autoplay=1" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
                            </body>
                        </html>
                    """.trimIndent()
                    
                    loadData(iframeHtml, "text/html", "utf-8")
                }
            }
        )
    }
}
