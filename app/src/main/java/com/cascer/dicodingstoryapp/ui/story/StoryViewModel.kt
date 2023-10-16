package com.cascer.dicodingstoryapp.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.data.repository.Repository
import com.cascer.dicodingstoryapp.utils.AppPreferences
import com.cascer.dicodingstoryapp.utils.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val repository: Repository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _stories = MutableLiveData<List<StoryDataModel>>()
    val stories: LiveData<List<StoryDataModel>> = _stories

    private val _story = MutableLiveData<StoryDataModel>()
    val story: LiveData<StoryDataModel> = _story

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _uploadMsg = MutableLiveData<String>()
    val uploadMsg: LiveData<String> = _uploadMsg

    private val _isUploadSuccess = MutableLiveData<Boolean>()
    val isUploadSuccess: LiveData<Boolean> = _isUploadSuccess

    fun requestStories() = viewModelScope.launch {
        _isLoading.postValue(true)
        when (val request = repository.stories()) {
            is Result.Success -> {
                _isLoading.postValue(false)
                _stories.postValue(request.data.listStory)
            }

            is Result.Error -> {
                _isLoading.postValue(false)
                _errorMsg.postValue(request.exception.message)
            }
        }
    }

    fun requestStory(id: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        when (val request = repository.story(id)) {
            is Result.Success -> {
                _isLoading.postValue(false)
                _story.postValue(request.data.story)
            }

            is Result.Error -> {
                _isLoading.postValue(false)
                _errorMsg.postValue(request.exception.message)
            }
        }
    }

    fun uploadStory(photo: File, desc: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        when (val request = repository.uploadStory(photo, desc)) {
            is Result.Success -> {
                _isLoading.postValue(false)
                _isUploadSuccess.postValue(true)
                _uploadMsg.postValue(request.data.message)
            }

            is Result.Error -> {
                _isLoading.postValue(false)
                _errorMsg.postValue(request.exception.message)
            }
        }
    }

    fun logout() = viewModelScope.launch {
        appPreferences.logout()
    }
}