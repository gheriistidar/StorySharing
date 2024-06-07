package com.dicoding.picodiploma.dicodingstory.di

import android.content.Context
import com.dicoding.picodiploma.dicodingstory.data.StoryRepository
import com.dicoding.picodiploma.dicodingstory.data.api.ApiConfig
import com.dicoding.picodiploma.dicodingstory.data.pref.UserPreference
import com.dicoding.picodiploma.dicodingstory.data.pref.dataStore
import com.dicoding.picodiploma.dicodingstory.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val database = StoryDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService(pref)
        return StoryRepository.getInstance(apiService, pref, database)
    }
}