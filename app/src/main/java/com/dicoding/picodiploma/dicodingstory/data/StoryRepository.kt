package com.dicoding.picodiploma.dicodingstory.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.dicodingstory.data.api.ApiService
import com.dicoding.picodiploma.dicodingstory.data.api.response.AddStoryResponse
import com.dicoding.picodiploma.dicodingstory.data.api.response.ListStoryItem
import com.dicoding.picodiploma.dicodingstory.data.api.response.LoginResponse
import com.dicoding.picodiploma.dicodingstory.data.api.response.RegisterResponse
import com.dicoding.picodiploma.dicodingstory.data.pref.UserModel
import com.dicoding.picodiploma.dicodingstory.data.pref.UserPreference
import com.dicoding.picodiploma.dicodingstory.database.StoryDatabase
import com.dicoding.picodiploma.dicodingstory.database.StoryRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val storyDatabase: StoryDatabase
) {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveUser(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getUser()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    suspend fun getStoriesWithLocation(): Result<List<ListStoryItem>> {
        return try {
            val response = apiService.getStoriesWithLocation()
            Result.Success(response.listStory)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun addStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null
    ): AddStoryResponse {
        return withContext(Dispatchers.IO) {
            apiService.addStory(file, description, lat, lon)
        }
    }


    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
            database: StoryDatabase
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference, database)
            }.also { instance = it }
    }
}