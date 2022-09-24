package com.example.carlockerwitharduino.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.carlockerwitharduino.model.UnLockingLogModel

@Dao
interface UnLockingDAO {

    @Insert
    suspend fun insertUnLockingLog(unLockingLogModel: UnLockingLogModel): Long

    @Query("DELETE FROM unLocking_table")
    suspend fun deleteAllUnLockingLogs(): Int

    @Query("SELECT * FROM unLocking_table")
    fun getAllUnLockingLog():LiveData<List<UnLockingLogModel>>

}