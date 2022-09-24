package com.example.carlockerwitharduino.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unLocking_table")
data class UnLockingLogModel(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "unlocking_or_locking_date")
    var unLockingDate: String,

    @ColumnInfo(name = "unlocking_or_locking_hour")
    var unLockingHour: String,

    var carName: String,
    var bluetoothMac: String,
    var isLock: Boolean

)