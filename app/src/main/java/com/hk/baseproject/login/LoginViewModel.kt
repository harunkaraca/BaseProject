package com.hk.baseproject.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.baseproject.data.BaseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.hk.baseproject.data.source.Result

class LoginViewModel @Inject constructor(private val baseRepository: BaseRepository, private val loginRepository: LoginRepository, private val sharedPreferences: SharedPreferences):
    ViewModel() {
    private var Tag="LoginViewModel"

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    init {
    }

    fun login(){
        _dataLoading.value=true
        viewModelScope.launch {
            val result=loginRepository.login()
            if(result is Result.Success){
                _isDataLoadingError.value = false
            }else if(result is Result.Error){
                _isDataLoadingError.value = true
                _snackbarText.value=result.exception.message
            }
            _dataLoading.value = false
        }
    }


}