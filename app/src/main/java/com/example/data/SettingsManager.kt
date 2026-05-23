package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val FLICKR_API_KEY = stringPreferencesKey("flickr_api")
        val BLOGGER_API_KEY = stringPreferencesKey("blogger_api")
        val YOUTUBE_API_KEY = stringPreferencesKey("youtube_api")
    }

    val isDarkModeFlow: Flow<Boolean> = context.dataStore.data.map { it[IS_DARK_MODE] ?: true }
    val flickrApiFlow: Flow<String> = context.dataStore.data.map { it[FLICKR_API_KEY] ?: "DEFAULT_FLICKR_KEY" }
    val bloggerApiFlow: Flow<String> = context.dataStore.data.map { it[BLOGGER_API_KEY] ?: "DEFAULT_BLOGGER_KEY" }
    val youtubeApiFlow: Flow<String> = context.dataStore.data.map { it[YOUTUBE_API_KEY] ?: "DEFAULT_YOUTUBE_KEY" }

    suspend fun setDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences -> preferences[IS_DARK_MODE] = isDark }
    }

    suspend fun setFlickrApiKey(key: String) {
        context.dataStore.edit { preferences -> preferences[FLICKR_API_KEY] = key }
    }

    suspend fun setBloggerApiKey(key: String) {
        context.dataStore.edit { preferences -> preferences[BLOGGER_API_KEY] = key }
    }

    suspend fun setYoutubeApiKey(key: String) {
        context.dataStore.edit { preferences -> preferences[YOUTUBE_API_KEY] = key }
    }
}
