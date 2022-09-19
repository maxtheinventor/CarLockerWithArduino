package com.example.carlockerwitharduino.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.database.CarRegisterDatabase
import com.example.carlockerwitharduino.databinding.FragmentCarConnectionBinding
import com.example.carlockerwitharduino.factory.CarRegisterViewModelFactory
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.view_model.CarConnectionViewModel
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel


class CarConnection : Fragment() {

    private lateinit var binding: FragmentCarConnectionBinding
    private lateinit var carConnectionViewModel: CarConnectionViewModel
    private lateinit var carRegisterViewModel: CarRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_car_connection,
            container,
            false
        )

        initCarConnectionViewModel()
        initCarRegisterViewModel()
        showStartConnectionWithCar()
        addCarToLocalDB()

        return binding.root

    }

    private fun startConnectionWithCar() {

        binding.apply {

            startStopCarConnectionCC.setOnClickListener {

                carConnectionViewModel.changeCarLoadingState()

                carConnectionViewModel.carConnectionLoading.observe(viewLifecycleOwner) { visibility ->

                    connectingToCarProgressBar.visibility =
                        if (visibility) View.VISIBLE else View.GONE

                }
            }
        }
    }

    private fun addCarToLocalDB() {

        binding.apply {

            addCarCC.setOnClickListener {

                startActivity(Intent(requireContext(), InfoToAddCar::class.java))

            }

        }

    }

    private fun initCarRegisterViewModel() {

        val dao = CarRegisterDatabase.getInstance(requireContext()).carRegisterDAO
        val repository = CarRegisterRepository(dao)
        val factory = CarRegisterViewModelFactory(repository)

        carRegisterViewModel = ViewModelProvider(this, factory)[CarRegisterViewModel::class.java]

    }

    private fun initCarConnectionViewModel() {

        carConnectionViewModel = ViewModelProvider(this)[CarConnectionViewModel::class.java]

    }

    private fun showStartConnectionWithCar() {

        carRegisterViewModel.getAllCarRegister.observe(
            requireActivity(),
            Observer { carRegisterList ->

                if (carRegisterList.size > 0) {

                    binding.apply {

                        startStopCarConnectionCC.visibility = View.VISIBLE
                        registerACarToContinueTitle.visibility = View.GONE
                        startConnectionWithCar()

                    }

                } else {

                    binding.apply {

                        binding.registerACarToContinueTitle.visibility = View.VISIBLE
                        binding.startStopCarConnectionCC.visibility = View.GONE

                    }


                }

            })


    }

}