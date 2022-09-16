package com.example.carlockerwitharduino.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carlockerwitharduino.dao.CarRegisterDAO
import com.example.carlockerwitharduino.model.CarRegister
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [CarRegister::class], version = 1)
abstract class CarRegisterDatabase : RoomDatabase() {

    abstract val carRegisterDAO: CarRegisterDAO

    companion object {

        @Volatile
        private var INSTANCE: CarRegisterDatabase? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): CarRegisterDatabase {
            kotlinx.coroutines.internal.synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CarRegisterDatabase::class.java,
                        "car_register_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}