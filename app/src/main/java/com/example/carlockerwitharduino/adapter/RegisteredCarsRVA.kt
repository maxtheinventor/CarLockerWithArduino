package com.example.carlockerwitharduino.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.databinding.RegisteredCarRowBinding
import com.example.carlockerwitharduino.model.CarRegister
import com.example.carlockerwitharduino.view.RegisteredCars

class RegisteredCarsRVA(context:Context) : RecyclerView.Adapter<MyViewHolder>() {

    private val registeredCarsArrayList = ArrayList<CarRegister>()
    private val context: Context = context

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

        holder.bind(registeredCarsArrayList[position])

        holder.binding.editRegisteredCar.setOnClickListener{
            Toast.makeText(context,"oa",Toast.LENGTH_SHORT).show()
        }

        holder.binding.deleteRegisteredCar.setOnClickListener{
            Toast.makeText(context,"aios",Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return registeredCarsArrayList.size
    }

    fun setList(carRegister: List<CarRegister>) {

        registeredCarsArrayList.clear()
        registeredCarsArrayList.addAll(carRegister)

    }

}

class MyViewHolder(val binding: RegisteredCarRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(carRegister: CarRegister) {

        binding.apply {

            carNameTextView.text = carRegister.carName
            bluetoothMacTextView.text = carRegister.carBluetoothMac


        }

    }

}