package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.R
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun StoryScreen(onNavigateToHome: () -> Unit) {
    val context = LocalContext.current
    var logs by remember { mutableStateOf(listOf("Initializing...")) }
    
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val readGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        val writeGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: false

        if (readGranted && writeGranted) {
            logs = logs + "Permissions Granted. Creating FolderMe..."
            createFolderMe(context) {
                logs = logs + "Folder Created. Navigating..."
                onNavigateToHome()
            }
        } else {
            logs = logs + "Permissions Denied. Skipping folder creation..."
            logs = logs + "Navigating..."
            onNavigateToHome()
        }
    }

    LaunchedEffect(Unit) {
        delay(1000)
        logs = logs + "Checking Permissions..."
        val hasRead = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val hasWrite = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        if (hasRead && hasWrite) {
            logs = logs + "Permissions verified. Checking FolderMe..."
            createFolderMe(context) {
                logs = logs + "Folder ready. Navigating to Home..."
                onNavigateToHome()
            }
        } else {
            logs = logs + "Requesting Permissions..."
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // CardView Pertama Berisi Image Cover
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Cover Image"
                )
                Text("Norax Kuadrat", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CardView Kedua Berisi LoadingView Elegan
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Loading...", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CardView Ketiga Tampilkan Log Aktifitas Loading
        Card(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Log Aktifitas:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                logs.forEach { log ->
                    Text("• $log", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

private fun createFolderMe(context: android.content.Context, onComplete: () -> Unit) {
    try {
        val extDir = context.getExternalFilesDir(null)
        if (extDir != null) {
            val folder = File(extDir, "FolderMe")
            if (!folder.exists()) {
                folder.mkdirs()
            }
        }
    } catch (e: Exception) {
         // Ignore
    } finally {
        onComplete()
    }
}
