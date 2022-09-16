package com.example.carlockerwitharduino.repository

import com.example.carlockerwitharduino.dao.CarRegisterDAO
import com.example.carlockerwitharduino.model.CarRegister

class CarRegisterRepository(private val dao: CarRegisterDAO) {

    val getAll = dao.getAllCarsInRegister()

    suspend fun insert(carRegister: CarRegister): Long {

        return dao.insertCarInRegister(carRegister)

    }

    suspend fun update(carRegister: CarRegister) : Int {

        return dao.updateCarInRegister(carRegister)

    }

    suspend fun delete(carRegister: CarRegister) : Int {

        return dao.deleteCarInRegister(carRegister)

    }

    suspend fun deleteAll() : Int {

        return dao.deleteAllCarsInRegister()

    }

}