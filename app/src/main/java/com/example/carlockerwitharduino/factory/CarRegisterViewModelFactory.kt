package com.example.carlockerwitharduino.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel

class CarRegisterViewModelFactory(private val repository: CarRegisterRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(CarRegisterViewModel::class.java)){
            return CarRegisterViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")

    }

}