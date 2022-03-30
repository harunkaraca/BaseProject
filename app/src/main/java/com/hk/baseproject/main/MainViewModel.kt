package com.hk.baseproject.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.baseproject.data.BaseRepository
import com.hk.baseproject.data.model.Country
import com.hk.baseproject.data.source.Result
import com.hk.baseproject.data.source.Result.Success
import com.hk.baseproject.data.source.Result.Error
import com.hk.baseproject.data.source.Result.Loading
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel  @Inject constructor(private val baseRepository: BaseRepository,private val sharedPreferences: SharedPreferences):ViewModel() {

    private val _items = MutableLiveData<List<Country>>().apply { value = emptyList() }
    val items: LiveData<List<Country>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError = _isDataLoadingError

    init {
//       loadCountry()
    }
    fun refresh(){
        loadCountry()
    }
    fun loadCountry(){
        Log.i("MainViewModel","run MainViewModel")
        _dataLoading.value = true
        viewModelScope.launch {
            val result=baseRepository.getCountryList(false)
            if(result is Success){
                _isDataLoadingError.value = false
                _items.value = result.data
            }else{
                _isDataLoadingError.value = false
                _items.value = emptyList()
            }
            _dataLoading.value = false
        }
    }
}