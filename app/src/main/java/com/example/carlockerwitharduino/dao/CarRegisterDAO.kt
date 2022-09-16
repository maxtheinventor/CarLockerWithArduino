package com.example.carlockerwitharduino.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.carlockerwitharduino.model.CarRegister

@Dao
interface CarRegisterDAO {

    @Insert
    suspend fun insertCarInRegister(carRegister: CarRegister) : Long

    @Update
    suspend fun updateCarInRegister(carRegister: CarRegister) : Int

    @Delete
    suspend fun deleteCarInRegister(carRegister: CarRegister) : Int

    @Query("DELETE FROM car_register_table")
    suspend fun deleteAllCarsInRegister() : Int

    @Query("SELECT * FROM car_register_table")
    fun getAllCarsInRegister():LiveData<List<CarRegister>>


}