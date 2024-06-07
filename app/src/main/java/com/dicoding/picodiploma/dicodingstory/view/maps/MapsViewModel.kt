package com.dicoding.picodiploma.dicodingstory.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.dicodingstory.data.Result
import com.dicoding.picodiploma.dicodingstory.data.StoryRepository
import com.dicoding.picodiploma.dicodingstory.data.api.response.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _storiesWithLocation = MutableLiveData<Result<List<ListStoryItem>>>()
    val storiesWithLocation: LiveData<Result<List<ListStoryItem>>>
        get() = _storiesWithLocation

    init {
        fetchStoriesWithLocation()
    }

    private fun fetchStoriesWithLocation() {
        viewModelScope.launch {
            _storiesWithLocation.value = Result.Loading
            try {
                _storiesWithLocation.value = repository.getStoriesWithLocation()
            } catch (e: Exception) {
                _storiesWithLocation.value = Result.Error(e.toString())
            }
        }
    }
}