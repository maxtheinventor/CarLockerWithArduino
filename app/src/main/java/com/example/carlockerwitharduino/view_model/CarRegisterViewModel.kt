package com.example.carlockerwitharduino.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carlockerwitharduino.event.CarRegisterEvent
import com.example.carlockerwitharduino.model.CarRegister
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.util.GlobalFunctions
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CarRegisterViewModel(private val repository: CarRegisterRepository) : ViewModel() {

    val getAllCarRegister = repository.getAll

    val carName = MutableLiveData<String>()
    val bluetoothMAC = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<CarRegisterEvent<String>>()

    val message: LiveData<CarRegisterEvent<String>>
        get() = statusMessage


    fun insert(context: Context) {

        viewModelScope.launch {

            val newRowID = repository.insert(CarRegister(0,carName.value!!,bluetoothMAC.value!!))

            if (newRowID > -1) {

                statusMessage.value = CarRegisterEvent("Car registered successfully!")
                GlobalFunctions.takeUserToCarControl(context)

            } else {

                statusMessage.value = CarRegisterEvent("An error has occurred registering the car")
                GlobalFunctions.takeUserToCarControl(context)

            }

        }

    }

    fun update(carRegister: CarRegister): Job =

        viewModelScope.launch {

            val numOfRowsUpdated = repository.update(carRegister)

            if (numOfRowsUpdated > -1) {

                //TODO: See if I change the name that the new one appears
                statusMessage.value = CarRegisterEvent("Data updated for: ${carRegister.carName}")

            } else {

                statusMessage.value = CarRegisterEvent("An error has occurred UPDATING the data ")

            }

        }

    fun delete(carRegister: CarRegister): Job =

        viewModelScope.launch {

            val numOfRowsDeleted = repository.delete(carRegister)

            if (numOfRowsDeleted > 0) {

                statusMessage.value = CarRegisterEvent("Car deleted from the register successfully")

            } else {

                statusMessage.value = CarRegisterEvent("An error has occurred deleting the data")

            }

        }

    fun deleteAll(): Job =

        viewModelScope.launch {

            val allRowsDeletedOrNot = repository.deleteAll()

            if (allRowsDeletedOrNot > 0) {

                statusMessage.value = CarRegisterEvent("All cars deleted from the register")

            } else {

                statusMessage.value = CarRegisterEvent("An error occurred deleting all data")

            }

        }

}