package com.dicoding.picodiploma.dicodingstory.view.addstory

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.dicodingstory.data.Result
import com.dicoding.picodiploma.dicodingstory.data.StoryRepository
import com.dicoding.picodiploma.dicodingstory.data.api.response.AddStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _uploadStatus = MutableLiveData<Result<AddStoryResponse>>()
    val uploadStatus: LiveData<Result<AddStoryResponse>>
        get() = _uploadStatus

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> get() = _description

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> get() = _imageUri

    fun setDescription(desc: String) {
        _description.value = desc
    }

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun uploadStory(
        multipartBody: MultipartBody.Part,
        requestBody: RequestBody,
        requestBodyLat: RequestBody?,
        requestBodyLon: RequestBody?
    ) {
        viewModelScope.launch {
            try {
                _uploadStatus.value = Result.Loading
                val response = repository.addStory(multipartBody, requestBody, requestBodyLat, requestBodyLon)
                if (response.error == false) {
                    _uploadStatus.value = Result.Success(response)
                } else {
                    _uploadStatus.value = Result.Error(response.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _uploadStatus.value = Result.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}