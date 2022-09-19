package com.example.carlockerwitharduino.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.adapter.RegisteredCarsRVA
import com.example.carlockerwitharduino.database.CarRegisterDatabase
import com.example.carlockerwitharduino.databinding.FragmentRegisteredCarsBinding
import com.example.carlockerwitharduino.factory.CarRegisterViewModelFactory
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.view_model.CarConnectionViewModel
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel


class RegisteredCars : Fragment() {

    private lateinit var binding: FragmentRegisteredCarsBinding
    private lateinit var adapter: RegisteredCarsRVA
    private lateinit var carRegisterViewModel: CarRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_registered_cars,
            container,
            false
        )

        initCarRegisterViewModel()
        initRecyclerView()

        return binding.root

    }

    private fun initCarRegisterViewModel() {

        val dao = CarRegisterDatabase.getInstance(requireContext()).carRegisterDAO
        val repository = CarRegisterRepository(dao)
        val factory = CarRegisterViewModelFactory(repository)

        carRegisterViewModel = ViewModelProvider(this, factory)[CarRegisterViewModel::class.java]

    }

    private fun initRecyclerView() {

        binding.apply {

            registeredCarsRV.layoutManager = LinearLayoutManager(requireActivity())
            adapter = RegisteredCarsRVA(requireActivity())
            registeredCarsRV.adapter = adapter
            displayRegisteredCarsList()

        }

    }

    private fun displayRegisteredCarsList() {

        carRegisterViewModel.getAllCarRegister.observe(requireActivity(), Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

    }

}