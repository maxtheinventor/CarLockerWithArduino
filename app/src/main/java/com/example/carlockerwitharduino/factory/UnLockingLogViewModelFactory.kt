package com.example.carlockerwitharduino.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carlockerwitharduino.repository.UnLockingLogRepository
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel
import com.example.carlockerwitharduino.view_model.UnLockingLogViewModel

class UnLockingLogViewModelFactory(private val repository: UnLockingLogRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(UnLockingLogViewModel::class.java)){
            return UnLockingLogViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")

    }


}