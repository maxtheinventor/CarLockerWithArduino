package com.example.carlockerwitharduino.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.adapter.RegisteredCarsRVA
import com.example.carlockerwitharduino.database.CarRegisterDatabase
import com.example.carlockerwitharduino.databinding.FragmentRegisteredCarsBinding
import com.example.carlockerwitharduino.factory.CarRegisterViewModelFactory
import com.example.carlockerwitharduino.model.CarRegisterModel
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.util.ToastsUtil
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel


class RegisteredCars : Fragment() {

    private lateinit var binding: FragmentRegisteredCarsBinding
    private lateinit var adapter: RegisteredCarsRVA
    private lateinit var carRegisterViewModel: CarRegisterViewModel
    private lateinit var carRegisterModelList: List<CarRegisterModel>
    private lateinit var carRegisterModel: CarRegisterModel

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
        addCarToLocalDB()
        deleteAllRegisteredCarsButtonFunctionality()

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
            adapter = RegisteredCarsRVA { selectedItem: CarRegisterModel, i: Int ->
                deleteOrUpdateCarRegister(
                    selectedItem,
                    i
                )
            }
            registeredCarsRV.adapter = adapter
            displayRegisteredCarsList()

        }

    }

    private fun displayRegisteredCarsList() {

        carRegisterViewModel.getAllCarRegister.observe(requireActivity(), Observer {

            carRegisterModelList = it
            showHideNoRegisteredCarsText()
            adapter.setList(carRegisterModelList)
            adapter.notifyDataSetChanged()

        })

    }

    private fun showHideNoRegisteredCarsText() {

        binding.apply {

            if (carRegisterModelList.isEmpty()) {

                registerACarToContinueTitle.visibility = View.VISIBLE
                deleteAllRegisteredCars.visibility = View.GONE

            } else if (carRegisterModelList.isNotEmpty()) {

                registerACarToContinueTitle.visibility = View.GONE
                deleteAllRegisteredCars.visibility = View.VISIBLE

            }
        }
    }

    private fun addCarToLocalDB() {

        binding.apply {

            addCar.setOnClickListener {

                startActivity(Intent(requireContext(), InfoToAddCar::class.java))

            }

        }

    }

    private fun deleteOrUpdateCarRegister(carRegisterModel: CarRegisterModel, selectedOption: Int) {

        setCarRegisterValues(carRegisterModel)

        //Update
        if (selectedOption == 1) {
            showUpdateDialog()
        }

        //Delete
        else if (selectedOption == 2) {
            carRegisterViewModel.delete(carRegisterModel)
        }

    }

    private fun showUpdateDialog() {

        val builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.update_registered_car_alert_dialog, null)

        val carNameEditText: EditText = dialogLayout.findViewById<EditText>(R.id.carNameURCAD)
        val bluetoothMacEditText: EditText =
            dialogLayout.findViewById<EditText>(R.id.bluetoothMacURCAD)

        with(builder) {

            setTitle("Update registered car")

            setView(dialogLayout)

            carNameEditText.setText(carRegisterModel.carName)
            bluetoothMacEditText.setText(carRegisterModel.carBluetoothMac)

            setPositiveButton(R.string.update) { _, _ ->

                if (carNameEditText.text.isNotEmpty() && bluetoothMacEditText.text.isNotEmpty()) {

                    carRegisterModel.carName = carNameEditText.text.toString()
                    carRegisterModel.carBluetoothMac = bluetoothMacEditText.text.toString()

                    carRegisterViewModel.update(carRegisterModel)

                } else {
                    ToastsUtil.fieldsCantBeEmptyToContinue(requireContext())
                }

            }

            setNegativeButton(R.string.cancel) { _, _ ->

            }

            setCancelable(false)

            show()

        }

    }

    private fun setCarRegisterValues(carRegisterModel: CarRegisterModel) {

        this.carRegisterModel = carRegisterModel

    }

    private fun deleteAllRegisteredCarsButtonFunctionality() {

        binding.deleteAllRegisteredCars.setOnClickListener{
            deleteAllRegisteredCarsAlertDialog()
        }

    }

    private fun deleteAllRegisteredCarsAlertDialog() {

        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle(R.string.alertDeletingAllData)
        alertDialogBuilder.setMessage("You are about to delete all of the registered cars from" +
                " the database \nAre you sure of that?")

        alertDialogBuilder.setPositiveButton(R.string.yes){_,_ ->
            carRegisterViewModel.deleteAll()
        }
        alertDialogBuilder.setNegativeButton(R.string.no){_,_ ->

        }

        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.show()

    }

}