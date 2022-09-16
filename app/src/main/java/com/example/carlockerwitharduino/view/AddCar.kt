package com.example.carlockerwitharduino.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.databinding.ActivityAddCarBinding
import com.example.carlockerwitharduino.databinding.ActivityInfoToAddCarBinding

class AddCar : AppCompatActivity() {

    private lateinit var binding: ActivityAddCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_car)

        registerCarInDB()

    }

    private fun registerCarInDB() {

        binding.doneAC.setOnClickListener{

            if(!checkIfEditTextAreEmpty()) {

                //Save in DB

            } else {

                emptyFieldErrorMessages()

            }

        }

    }

    private fun checkIfEditTextAreEmpty():Boolean {

        binding.apply {

            return !(carNameAC.text.isNotEmpty() && bluetoothMACAC.text.isNotEmpty())

        }

    }

    private fun emptyFieldErrorMessages() {

        binding.apply {

            if(carNameAC.text.isEmpty()) {

                carNameAC.error = getString(R.string.fieldCantBeEmpty)

            }

            if(bluetoothMACAC.text.isEmpty()) {

                bluetoothMACAC.error = getString(R.string.fieldCantBeEmpty)

            }

        }

    }

}