package com.dicoding.picodiploma.dicodingstory.view.storydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.dicodingstory.data.api.response.ListStoryItem

class StoryDetailViewModel : ViewModel() {
    private val _story = MutableLiveData<ListStoryItem>()
    val story: LiveData<ListStoryItem> get() = _story

    fun setStoryDetail(story: ListStoryItem) {
        _story.value = story
    }
}