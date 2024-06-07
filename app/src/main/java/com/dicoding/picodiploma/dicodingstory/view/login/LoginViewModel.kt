package com.dicoding.picodiploma.dicodingstory.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.dicodingstory.data.Result
import com.dicoding.picodiploma.dicodingstory.data.StoryRepository
import com.dicoding.picodiploma.dicodingstory.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _loginStatus = MutableLiveData<Result<UserModel>>()
    val loginStatus: LiveData<Result<UserModel>>
        get() = _loginStatus

    private fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginStatus.value = Result.Loading
                val response = repository.login(email, password)
                if (response.error == false && response.loginResult?.token != null) {
                    val user = UserModel(email, response.loginResult.token)
                    saveSession(user)
                    _loginStatus.value = Result.Success(user)
                } else {
                    _loginStatus.value = Result.Error(response.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _loginStatus.value = Result.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}