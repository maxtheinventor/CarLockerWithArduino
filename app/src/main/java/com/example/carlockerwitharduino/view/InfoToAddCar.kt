package com.example.carlockerwitharduino.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.databinding.ActivityInfoToAddCarBinding

class InfoToAddCar : AppCompatActivity() {

    private lateinit var binding: ActivityInfoToAddCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_info_to_add_car)

        goToAddCarToDB()

    }

    private fun goToAddCarToDB() {

        binding.nextITAC.setOnClickListener{

            startActivity(Intent(this,AddCar::class.java))

        }

    }

}