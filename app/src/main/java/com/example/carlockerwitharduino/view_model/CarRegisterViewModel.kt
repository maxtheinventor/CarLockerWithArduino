package com.example.carlockerwitharduino.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carlockerwitharduino.event.CarRegisterEvent
import com.example.carlockerwitharduino.model.CarRegisterModel
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.util.GlobalFunctions
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CarRegisterViewModel(private val repository: CarRegisterRepository) : ViewModel() {

    val getAllCarRegister = repository.getAll

    val carName = MutableLiveData<String>()
    val bluetoothMAC = MutableLiveData<String>()

    private val databaseStatusMessage = MutableLiveData<CarRegisterEvent<String>>()

    val message: LiveData<CarRegisterEvent<String>>
        get() = databaseStatusMessage


    fun insert(context: Context) {

        viewModelScope.launch {

            val newRowID = repository.insert(CarRegisterModel(0, carName.value!!, bluetoothMAC.value!!))

            if (newRowID > -1) {

                databaseStatusMessage.value = CarRegisterEvent("Car registered successfully!")
                GlobalFunctions.takeUserToCarControl(context)

            } else {

                databaseStatusMessage.value =
                    CarRegisterEvent("An error has occurred registering the car")
                GlobalFunctions.takeUserToCarControl(context)

            }

        }

    }

    fun update(carRegisterModel: CarRegisterModel): Job =

        viewModelScope.launch {

            val numOfRowsUpdated = repository.update(carRegisterModel)

            if (numOfRowsUpdated > -1) {

                //TODO: See if I change the name that the new one appears
                databaseStatusMessage.value =
                    CarRegisterEvent("Data updated for: ${carRegisterModel.carName}")

            } else {

                databaseStatusMessage.value =
                    CarRegisterEvent("An error has occurred UPDATING the data ")

            }

        }

    fun delete(carRegisterModel: CarRegisterModel): Job =

        viewModelScope.launch {

            val numOfRowsDeleted = repository.delete(carRegisterModel)

            if (numOfRowsDeleted > 0) {

                databaseStatusMessage.value =
                    CarRegisterEvent("Car deleted from the register successfully")

            } else {

                databaseStatusMessage.value =
                    CarRegisterEvent("An error has occurred deleting the data")

            }

        }

    fun deleteAll(): Job =

        viewModelScope.launch {

            val allRowsDeletedOrNot = repository.deleteAll()

            if (allRowsDeletedOrNot > 0) {

                databaseStatusMessage.value = CarRegisterEvent("All cars deleted from the register")

            } else {

                databaseStatusMessage.value =
                    CarRegisterEvent("An error occurred deleting all data")

            }

        }

}