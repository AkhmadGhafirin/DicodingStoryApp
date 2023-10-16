package com.cascer.dicodingstoryapp.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cascer.dicodingstoryapp.data.model.LoginRequest
import com.cascer.dicodingstoryapp.data.model.RegisterRequest
import com.cascer.dicodingstoryapp.data.repository.Repository
import com.cascer.dicodingstoryapp.utils.AppPreferences
import com.cascer.dicodingstoryapp.utils.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: Repository, private val appPreferences: AppPreferences
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    private val _isRegisterSuccess = MutableLiveData<Boolean>()
    val isRegisterSuccess: LiveData<Boolean> = _isRegisterSuccess

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    fun register(param: RegisterRequest) = viewModelScope.launch {
        _isLoading.postValue(true)
        when (val result = repository.register(param)) {
            is Result.Success -> {
                _isLoading.postValue(false)
                _isRegisterSuccess.postValue(true)
            }

            is Result.Error -> {
                _isLoading.postValue(false)
                _errorMsg.postValue(result.exception.message)
            }
        }
    }

    fun login(param: LoginRequest) = viewModelScope.launch {
        _isLoading.postValue(true)
        when (val result = repository.login(param)) {
            is Result.Success -> {
                _isLoading.postValue(false)
                appPreferences.token = "Bearer ${result.data.loginResult.token}"
                _isLoginSuccess.postValue(true)
            }

            is Result.Error -> {
                _isLoading.postValue(false)
                _errorMsg.postValue(result.exception.message)
            }
        }
    }

    fun checkIsLogin() = viewModelScope.launch {
        delay(1000L)
        _isLogin.postValue(appPreferences.token.isNotEmpty())
    }
}