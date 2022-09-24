package com.example.carlockerwitharduino.view_model

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carlockerwitharduino.event.UnLockingLogEvent
import com.example.carlockerwitharduino.model.UnLockingLogModel
import com.example.carlockerwitharduino.repository.UnLockingLogRepository
import com.example.carlockerwitharduino.util.GlobalFunctions
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UnLockingLogViewModel(private val repository: UnLockingLogRepository) : ViewModel() {

    private val _unLockingDate = MutableLiveData<String>()
    private val _unLockingHour = MutableLiveData<String>()
    private val _carName = MutableLiveData<String>()
    private val _bluetoothMac = MutableLiveData<String>()
    private val _isLock = MutableLiveData<Boolean>()

    val unLockingDate: LiveData<String> get() = _unLockingDate
    val unLockingHour: LiveData<String> get() = _unLockingHour
    val carName: LiveData<String> get() = _carName
    val bluetoothMac: LiveData<String> get() = _bluetoothMac
    val isLock: LiveData<Boolean> get() = _isLock

    private val databaseStatusMessage = MutableLiveData<UnLockingLogEvent<String>>()

    val getAllUnLockingLogRegister = repository.getAll

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUnLockingDate() {
        _unLockingDate.value = GlobalFunctions.getCurrentDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUnLockingHour() {
        _unLockingHour.value = GlobalFunctions.getCurrentHourAndMinutes()
    }

    fun setCarName(carName: String) {
        _carName.value = carName
    }

    fun setBluetoothMac(bluetoothName: String) {
        _bluetoothMac.value = bluetoothName
    }

    fun setIsLock(isLock: Boolean) {
        _isLock.value = isLock
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert(context: Context) {

        viewModelScope.launch {

            setUnLockingDate()
            setUnLockingHour()

            val newRowID =
                repository.insert(
                    UnLockingLogModel(
                        0, unLockingDate.value!!, unLockingHour.value!!,
                        carName.value!!, bluetoothMac.value!!, isLock.value!!
                    )
                )

            if (newRowID > -1) {
                println("Hola")
            } else {
                databaseStatusMessage.value =
                    UnLockingLogEvent("An error has occurred saving the log")
            }

        }

    }

    fun deleteAll(): Job =

        viewModelScope.launch {

            val allRowsDeletedOrNot = repository.deleteAll()

            if (allRowsDeletedOrNot > 0) {
                databaseStatusMessage.value =
                    UnLockingLogEvent("All logs deleted from the database")
            } else {
                databaseStatusMessage.value =
                    UnLockingLogEvent("An error occurred deleting everything")
            }

        }
}