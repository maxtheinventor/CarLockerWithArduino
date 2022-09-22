package com.example.carlockerwitharduino.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import com.example.carlockerwitharduino.util.GlobalFunctions
import com.example.carlockerwitharduino.util.ToastsUtil

class PreEstablishBluetoothConnectionChecks constructor() {

    companion object {

        private lateinit var bluetoothManager: BluetoothManager
        private lateinit var bluetoothAdapter: BluetoothAdapter

        fun initChecks(context: Context): Boolean {

            bluetoothManager = getSystemService(context, BluetoothManager::class.java)!!
            bluetoothAdapter = bluetoothManager.getAdapter()
            return checkIfDeviceSupportsBluetooth(context)

        }

        private fun checkIfDeviceSupportsBluetooth(context: Context): Boolean {

            var result: Boolean = false

            if (bluetoothAdapter == null) {

                ToastsUtil.yourDeviceDoesntSupportBluetooth(context)

            } else {
                result = checkBluetoothStatus(context)
            }

            return result

        }

        private fun checkBluetoothStatus(context: Context): Boolean {

            var result: Boolean = false

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

            if (!bluetoothAdapter?.isEnabled) {

                GlobalFunctions.enableBluetoothToContinue(context)

            } else {
                result = true
            }

            return result

        }

    }

}