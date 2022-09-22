package com.example.carlockerwitharduino.model

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket

class ArduinoBluetooth {

    companion object {

        lateinit var bluetoothManager: BluetoothManager
        lateinit var bluetoothAdapter: BluetoothAdapter
        lateinit var btSocket: BluetoothSocket
        lateinit var hc05Car: BluetoothDevice

    }

}