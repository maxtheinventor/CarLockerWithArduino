package com.example.carlockerwitharduino.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.database.CarRegisterDatabase
import com.example.carlockerwitharduino.databinding.RegisteredCarRowBinding
import com.example.carlockerwitharduino.factory.CarRegisterViewModelFactory
import com.example.carlockerwitharduino.model.CarRegisterModel
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.view.RegisteredCars
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel

class RegisteredCarsRVA(private val clickListener: (CarRegisterModel, i:Int) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private val registeredCarsArrayList = ArrayList<CarRegisterModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: RegisteredCarRowBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.registered_car_row, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(registeredCarsArrayList[position], clickListener)

    }

    override fun getItemCount(): Int {
        return registeredCarsArrayList.size
    }

    fun setList(carRegisterModel: List<CarRegisterModel>) {

        registeredCarsArrayList.clear()
        registeredCarsArrayList.addAll(carRegisterModel)

    }

}

class MyViewHolder(val binding: RegisteredCarRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(carRegisterModel: CarRegisterModel, clickListener: (CarRegisterModel, i: Int) -> Unit) {

        binding.apply {

            carNameTextView.text = carRegisterModel.carName
            bluetoothMacTextView.text = carRegisterModel.carBluetoothMac

            editRegisteredCar.setOnClickListener {
                clickListener(carRegisterModel,1)
            }

            deleteRegisteredCar.setOnClickListener {
                clickListener(carRegisterModel,2)
            }

        }

    }

}