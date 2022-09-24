package com.example.carlockerwitharduino.repository

import com.example.carlockerwitharduino.dao.CarRegisterDAO
import com.example.carlockerwitharduino.model.CarRegisterModel

class CarRegisterRepository(private val dao: CarRegisterDAO) {

    val getAll = dao.getAllCarsInRegister()

    suspend fun insert(carRegisterModel: CarRegisterModel): Long {

        return dao.insertCarInRegister(carRegisterModel)

    }

    suspend fun update(carRegisterModel: CarRegisterModel) : Int {

        return dao.updateCarInRegister(carRegisterModel)

    }

    suspend fun delete(carRegisterModel: CarRegisterModel) : Int {

        return dao.deleteCarInRegister(carRegisterModel)

    }

    suspend fun deleteAll() : Int {

        return dao.deleteAllCarsInRegister()

    }

}