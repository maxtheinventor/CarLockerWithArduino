package com.example.carlockerwitharduino.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_register_table")
data class CarRegisterModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    var carID: Int,

    @ColumnInfo(name = "car_name")
    var carName: String,

    @ColumnInfo(name = "car_bluetooth_mac")
    var carBluetoothMac: String

)
