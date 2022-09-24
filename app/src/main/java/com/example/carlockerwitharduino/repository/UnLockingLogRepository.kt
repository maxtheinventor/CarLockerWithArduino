package com.example.carlockerwitharduino.repository

import com.example.carlockerwitharduino.dao.UnLockingDAO
import com.example.carlockerwitharduino.model.UnLockingLogModel

class UnLockingLogRepository(private val dao: UnLockingDAO) {

    val getAll = dao.getAllUnLockingLog()

    suspend fun insert(unLockingLogModel: UnLockingLogModel): Long {
        return dao.insertUnLockingLog(unLockingLogModel)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAllUnLockingLogs()
    }

}