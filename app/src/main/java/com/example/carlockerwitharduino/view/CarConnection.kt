package com.example.carlockerwitharduino.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.databinding.FragmentCarConnectionBinding
import com.example.carlockerwitharduino.view_model.CarConnectionViewModel


class CarConnection : Fragment() {

    private lateinit var binding: FragmentCarConnectionBinding
    private lateinit var viewModel: CarConnectionViewModel

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

        viewModel = ViewModelProvider(this)[CarConnectionViewModel::class.java]

        startConnectionWithCar()
        addCarToLocalDB()

        return binding.root

    }

    private fun startConnectionWithCar() {

        binding.apply {

            startStopCarConnectionCC.setOnClickListener {

                viewModel.changeCarLoadingState()

                viewModel.carConnectionLoading.observe(viewLifecycleOwner) { visibility ->

                    connectingToCarProgressBar.visibility =
                        if (visibility) View.VISIBLE else View.GONE

                }
            }
        }
    }

    private fun addCarToLocalDB() {

        binding.apply {

            addCarCC.setOnClickListener {

                startActivity(Intent(requireContext(),InfoToAddCar::class.java))

            }

        }

    }

}