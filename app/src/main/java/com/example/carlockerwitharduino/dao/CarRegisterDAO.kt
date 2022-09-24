package com.example.carlockerwitharduino.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.carlockerwitharduino.model.CarRegisterModel

@Dao
interface CarRegisterDAO {

    @Insert
    suspend fun insertCarInRegister(carRegisterModel: CarRegisterModel) : Long

    @Update
    suspend fun updateCarInRegister(carRegisterModel: CarRegisterModel) : Int

    @Delete
    suspend fun deleteCarInRegister(carRegisterModel: CarRegisterModel) : Int

    @Query("DELETE FROM car_register_table")
    suspend fun deleteAllCarsInRegister() : Int

    @Query("SELECT * FROM car_register_table")
    fun getAllCarsInRegister():LiveData<List<CarRegisterModel>>


}