package com.example.carlockerwitharduino

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.carlockerwitharduino.databinding.ActivityStablishConnectionWithArduinoBoardBinding
import com.example.carlockerwitharduino.util.GlobalFunctions
import com.example.carlockerwitharduino.util.ToastsUtil
import java.io.IOException
import java.util.*

class EstablishConnectionWithArduinoBoard : AppCompatActivity() {

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var btSocket: BluetoothSocket
    private lateinit var hc05Car: BluetoothDevice
    private val mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var counter = 0
    private lateinit var binding: ActivityStablishConnectionWithArduinoBoardBinding
    private var value:Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_stablish_connection_with_arduino_board
        )

        bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.getAdapter()
        hc05Car = bluetoothAdapter.getRemoteDevice("98:D3:51:F5:DF:08")

        checkIfDeviceSupportsBluetooth()

    }

    fun checkIfDeviceSupportsBluetooth() {

        if (bluetoothAdapter == null) {
            ToastsUtil.yourDeviceDoesntSupportBluetooth(this)
        } else {
            checkBluetoothStatus()
        }

    }

    private fun checkBluetoothStatus() {
        Toast.makeText(applicationContext, "It has started", Toast.LENGTH_SHORT).show()
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        if (bluetoothAdapter?.isEnabled == false) {

            GlobalFunctions.enableBluetoothToContinue(this)

        } else {

            connectToBoard()

        }


    }

    fun connectToBoard() {

        val device = bluetoothAdapter.getRemoteDevice(hc05Car.toString())
        Log.d("", "Connecting to ... $device")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        Toast.makeText(
            applicationContext,
            "Connecting to ... ${device.name} mac: ${device.uuids[0]} address: ${device.address}",
            Toast.LENGTH_LONG
        ).show()
        bluetoothAdapter.cancelDiscovery()
        try {
            btSocket = device.createRfcommSocketToServiceRecord(mUUID)
            btSocket.connect()
            Log.d("", "Connection made.")
            Toast.makeText(applicationContext, "Connection made.", Toast.LENGTH_SHORT).show()
            writeData("a")
            receiveData()
        } catch (e: IOException) {
            try {
                btSocket.close()
            } catch (e2: IOException) {
                Log.d("", "Unable to end the connection")
                Toast.makeText(
                    applicationContext,
                    "Unable to end the connection",
                    Toast.LENGTH_SHORT
                ).show()
            }

            Log.d("", "Socket creation failed")
            Toast.makeText(applicationContext, "Socket creation failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun writeData(data: String) {
        var outStream = btSocket.outputStream
        try {
            outStream = btSocket.outputStream
        } catch (e: IOException) {
            Log.d(TAG, "Bug BEFORE Sending data", e)
        }
        val msgBuffer = data.toByteArray()

        try {
            outStream.write(msgBuffer)
        } catch (e: IOException) {
            Log.d(TAG, "Bug while sending data", e)
        }

    }

    private fun receiveData() {

        var inputStream = btSocket.inputStream
        try {

        } catch (e: IOException) {
            Log.d(TAG,"Bug BEFORE Receiving data")
        }

        val msgBuffer = ByteArray(1024)
        var bytes: Int

        try {

            bytes = inputStream.read(msgBuffer)

            //This is the character we receive
            val readMessage = String (msgBuffer,0, bytes)


        } catch (e: IOException) {
            Log.d(TAG, "Bug while Receiving data", e)
        }


    }


}
