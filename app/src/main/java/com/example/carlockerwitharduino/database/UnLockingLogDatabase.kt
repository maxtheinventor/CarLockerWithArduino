package com.example.carlockerwitharduino.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carlockerwitharduino.dao.UnLockingDAO
import com.example.carlockerwitharduino.model.UnLockingLogModel
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [UnLockingLogModel::class], version = 1)
abstract class UnLockingLogDatabase: RoomDatabase() {

    abstract val unLockingDAO: UnLockingDAO

    companion object {

        @Volatile
        private var INSTANCE: UnLockingLogDatabase? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): UnLockingLogDatabase {
            kotlinx.coroutines.internal.synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UnLockingLogDatabase::class.java,
                        "unLocking_table"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }


    }

}