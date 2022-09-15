package com.example.carlockerwitharduino.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarConnectionViewModel : ViewModel() {

    private val _carConnectionLoading = MutableLiveData<Boolean>()
    val carConnectionLoading: LiveData<Boolean> get() = _carConnectionLoading

    init {
        _carConnectionLoading.value = false
    }

    fun changeCarLoadingState() {

        _carConnectionLoading.value = _carConnectionLoading.value != true

    }

}