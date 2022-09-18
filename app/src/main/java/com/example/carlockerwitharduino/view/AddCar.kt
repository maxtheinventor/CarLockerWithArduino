package com.example.carlockerwitharduino.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.database.CarRegisterDatabase
import com.example.carlockerwitharduino.databinding.ActivityAddCarBinding
import com.example.carlockerwitharduino.databinding.ActivityInfoToAddCarBinding
import com.example.carlockerwitharduino.factory.CarRegisterViewModelFactory
import com.example.carlockerwitharduino.model.CarRegister
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.util.ToastsUtil
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel

class AddCar : AppCompatActivity() {

    private lateinit var binding: ActivityAddCarBinding
    private lateinit var viewModel: CarRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_car)

        val dao = CarRegisterDatabase.getInstance(application).carRegisterDAO
        val repository = CarRegisterRepository(dao)
        val factory = CarRegisterViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(CarRegisterViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this

        registerCarInDB()
        listenToDBActionsToasts()

    }

    private fun registerCarInDB() {

        binding.doneAC.setOnClickListener{

            if(!checkIfEditTextAreEmpty()) {

                viewModel.insert(this)

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

    private fun listenToDBActionsToasts() {

        viewModel.message.observe(this, Observer {

            it.getContentIfNotHandled()?.let {
                ToastsUtil.carRegisterDatabaseAction(this,it)
            }

        })

    }

}