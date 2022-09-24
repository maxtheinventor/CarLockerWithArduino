package com.example.carlockerwitharduino.view_model

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carlockerwitharduino.model.ArduinoBluetooth
import java.io.IOException
import java.util.*

class ArduinoBluetoothViewModel : ViewModel() {

    private val _carName = MutableLiveData<String>()
    private val _bluetoothMac = MutableLiveData<String>()
    private val _connectionStatus = MutableLiveData<Boolean>()
    private val _initialArduinoWriteStatus = MutableLiveData<Boolean>()

    val carName: LiveData<String> get() = _carName
    val bluetoothMac: LiveData<String> get() = _bluetoothMac
    val connectionStatus: LiveData<Boolean> get() = _connectionStatus
    val initialArduinoWriteStatus: LiveData<Boolean> get() = _initialArduinoWriteStatus

    init {
        _connectionStatus.value = false
    }

    fun setCarName(carName: String) {
        _carName.value = carName
    }

    fun setBluetoothMac(bluetoothMac: String) {
        _bluetoothMac.value = bluetoothMac
    }

    fun setConnectionStatus(status: Boolean) {
        _connectionStatus.value = status
    }

    fun connectToArduinoBoard(lifecycleOwner: LifecycleOwner, context: Context) {

        val mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var arduinoBluetooth: ArduinoBluetooth = ArduinoBluetooth()

        arduinoBluetooth.apply {

            ArduinoBluetooth.bluetoothManager = ContextCompat.getSystemService(
                context,
                BluetoothManager::class.java
            )!!
            ArduinoBluetooth.bluetoothAdapter = ArduinoBluetooth.bluetoothManager.getAdapter()

            bluetoothMac.observe(lifecycleOwner) {
                ArduinoBluetooth.hc05Car = ArduinoBluetooth.bluetoothAdapter.getRemoteDevice(it)
            }


            val device =
                ArduinoBluetooth.bluetoothAdapter.getRemoteDevice(ArduinoBluetooth.hc05Car.toString())

            carName.observe(lifecycleOwner) {

                Toast.makeText(
                    context,
                    "Connecting to car ... $it",
                    Toast.LENGTH_LONG
                ).show()

            }


            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            ArduinoBluetooth.bluetoothAdapter.cancelDiscovery()

            try {

                ArduinoBluetooth.btSocket = device.createRfcommSocketToServiceRecord(mUUID)
                ArduinoBluetooth.btSocket.connect()

                Toast.makeText(context, "Connection made.", Toast.LENGTH_SHORT).show()
                setConnectionStatus(true)


            } catch (e: IOException) {

                try {
                    ArduinoBluetooth.btSocket.close()
                } catch (e2: IOException) {

                    Log.d("", "Unable to end the connection")
                    Toast.makeText(
                        context,
                        "Unable to end the connection",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                Log.d("", "Socket creation failed")
                Toast.makeText(context, "Socket creation failed", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun writeData(data: String) {

        var outStream = ArduinoBluetooth.btSocket.outputStream
        try {
            outStream = ArduinoBluetooth.btSocket.outputStream
        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "Bug BEFORE Sending data", e)
        }
        val msgBuffer = data.toByteArray()

        try {
            outStream.write(msgBuffer)
        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "Bug while sending data", e)
        }

    }

    fun receiveData(): String {

        var readMessage: String = ""


        var inputStream = ArduinoBluetooth.btSocket.inputStream
        try {

        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "Bug BEFORE Receiving data")
        }

        val msgBuffer = ByteArray(1024)
        var bytes: Int

        try {

            bytes = inputStream.read(msgBuffer)

            //This is the character we receive
            readMessage = String(msgBuffer, 0, bytes)


        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "Bug while Receiving data", e)
        }

        return readMessage


    }

    fun initialArduinoWrite() {

        writeData("a")
        if (receiveData() == "1") {
            _initialArduinoWriteStatus.value = true
        }

    }

    fun openCar() {
        writeData("o")
        if (receiveData() == "2") {

        }
    }

    fun closeCar() {
        writeData("c")
        if (receiveData() == "3") {

        }
    }

}