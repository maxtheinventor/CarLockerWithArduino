package com.example.carlockerwitharduino.view

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.database.CarRegisterDatabase
import com.example.carlockerwitharduino.factory.CarRegisterViewModelFactory
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.bluetooth.PreEstablishBluetoothConnectionChecks
import com.example.carlockerwitharduino.database.UnLockingLogDatabase
import com.example.carlockerwitharduino.databinding.FragmentCarConnectionAndControlBinding
import com.example.carlockerwitharduino.factory.UnLockingLogViewModelFactory
import com.example.carlockerwitharduino.repository.UnLockingLogRepository
import com.example.carlockerwitharduino.util.GlobalFunctions
import com.example.carlockerwitharduino.view_model.ArduinoBluetoothViewModel
import com.example.carlockerwitharduino.view_model.CarConnectionViewModel
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel
import com.example.carlockerwitharduino.view_model.UnLockingLogViewModel


class CarConnectionAndControl : Fragment() {

    private lateinit var binding: FragmentCarConnectionAndControlBinding
    private lateinit var carConnectionViewModel: CarConnectionViewModel
    private lateinit var carRegisterViewModel: CarRegisterViewModel
    private lateinit var arduinoBluetoothViewModel: ArduinoBluetoothViewModel
    private lateinit var unLockingLogViewModel: UnLockingLogViewModel
    private var lockUnlockStatus: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_car_connection_and_control,
            container,
            false
        )

        initViewModels()
        showStartConnectionWithCar()
        checkConnectionStatus()
        checkInitialArduinoWrite()
        connectViaBluetoothButtonFunctionality()
        connectViaNFCFunctionallity()
        lockUnlockCar()

        return binding.root

    }

    private fun initViewModels() {

        initCarConnectionViewModel()
        initCarRegisterViewModel()
        initArduinoBluetoothViewModel()
        initUnLockingLogViewModel()

    }

    private fun startConnectionWithCar() {

        binding.startStopCarConnectionCC.setOnClickListener {

            if (PreEstablishBluetoothConnectionChecks.initChecks(requireContext())) {

                changeCarLoadingState()
                selectRegisteredCarsAlertDialog()

            }

        }
    }

    private fun changeCarLoadingState() {

        carConnectionViewModel.changeCarLoadingState()

        carConnectionViewModel.carConnectionLoading.observe(viewLifecycleOwner) { visibility ->

            binding.connectingToCarProgressBar.visibility =
                if (visibility) View.VISIBLE else View.GONE

        }

    }

    private fun checkConnectionStatus() {

        arduinoBluetoothViewModel.connectionStatus.observe(
            viewLifecycleOwner,
            Observer { connectionStatus ->

                if (connectionStatus) {

                    arduinoBluetoothViewModel.initialArduinoWrite()

                }
            })

    }

    private fun checkInitialArduinoWrite() {

        arduinoBluetoothViewModel.initialArduinoWriteStatus.observe(viewLifecycleOwner, Observer {

            if (it) {
                showConnectViaHideConnectionButtons()
            }

        })

    }

    private fun initCarConnectionViewModel() {
        carConnectionViewModel = ViewModelProvider(this)[CarConnectionViewModel::class.java]
    }

    private fun initCarRegisterViewModel() {

        val dao = CarRegisterDatabase.getInstance(requireContext()).carRegisterDAO
        val repository = CarRegisterRepository(dao)
        val factory = CarRegisterViewModelFactory(repository)

        carRegisterViewModel = ViewModelProvider(this, factory)[CarRegisterViewModel::class.java]

    }

    private fun initArduinoBluetoothViewModel() {
        arduinoBluetoothViewModel = ViewModelProvider(this)[ArduinoBluetoothViewModel::class.java]
    }

    private fun initUnLockingLogViewModel() {

        val dao = UnLockingLogDatabase.getInstance(requireContext()).unLockingDAO
        val repository = UnLockingLogRepository(dao)
        val factory = UnLockingLogViewModelFactory(repository)

        unLockingLogViewModel = ViewModelProvider(this, factory)[UnLockingLogViewModel::class.java]

    }

    private fun showStartConnectionWithCar() {

        carRegisterViewModel.getAllCarRegister.observe(
            requireActivity(),
            Observer { carRegisterList ->

                if (carRegisterList.isNotEmpty()) {

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

    private fun selectRegisteredCarsAlertDialog() {

        val builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.select_registered_car_alert_dialog, null)

        val allRegisteredCarsSpinner: Spinner =
            dialogLayout.findViewById(R.id.allRegisteredCarsSpinner)

        var listOfMacAddress: ArrayList<String> = arrayListOf()

        with(builder) {

            setTitle("CAR TO CONNECT TO:")

            setView(dialogLayout)

            if (allRegisteredCarsSpinner != null) {

                val allRegisteredCars = context?.let {
                    ArrayAdapter<Any>(
                        it,
                        androidx.transition.R.layout.support_simple_spinner_dropdown_item
                    )
                }

                carRegisterViewModel.getAllCarRegister.observe(viewLifecycleOwner, Observer {

                    it?.forEach {
                        allRegisteredCars?.add(it.carName)
                        listOfMacAddress.add(it.carBluetoothMac)
                    }

                    allRegisteredCarsSpinner.adapter = allRegisteredCars

                })

                allRegisteredCarsSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener,
                        AdapterView.OnItemClickListener {

                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            position: Int,
                            p3: Long
                        ) {

                            arduinoBluetoothViewModel.setCarName(
                                allRegisteredCars!!.getItem(
                                    position
                                ) as String
                            )
                            arduinoBluetoothViewModel.setBluetoothMac(listOfMacAddress.get(position))

                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {}
                        override fun onItemClick(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                        }

                    }

            }

            setPositiveButton("NEXT") { dialog, _ ->

                arduinoBluetoothViewModel.connectToArduinoBoard(requireActivity(), requireContext())
                changeCarLoadingState()
                dialog.dismiss()

            }

            setNegativeButton("CANCEL") { dialog, _ ->
                changeCarLoadingState()
                dialog.dismiss()
            }

            setCancelable(false)

            show()

        }

    }

    private fun showConnectViaHideConnectionButtons() {

        binding.apply {

            startStopCarConnectionCC.visibility = View.GONE
            connectViaBluetooth.visibility = View.VISIBLE
            connectViaNFC.visibility = View.VISIBLE

        }

    }

    private fun connectViaBluetoothButtonFunctionality() {

        binding.apply {

            connectViaBluetooth.setOnClickListener {
                hideConnectViaButtons()
                showLockUnlockCarButton()
            }

        }

    }

    private fun connectViaNFCFunctionallity() {

        binding.apply {

            connectViaNFC.setOnClickListener {
                GlobalFunctions.nfcFunctionalityNotAvailableYet(requireContext())
            }

        }

    }

    private fun hideConnectViaButtons() {

        binding.apply {

            connectViaBluetooth.visibility = View.GONE
            connectViaNFC.visibility = View.GONE

        }

    }

    private fun showLockUnlockCarButton() {
        binding.lockUnlockCar.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun lockUnlockCar() {

        binding.apply {

            lockUnlockCar.setOnClickListener {

                if (!lockUnlockStatus) {

                    setUnLockingLogValues(false)
                    arduinoBluetoothViewModel.openCar()
                    unLockingLogViewModel.insert(requireContext())
                    changeLockUnlockCarImageToOpen()
                    lockUnlockStatus = true

                } else {

                    setUnLockingLogValues(true)
                    arduinoBluetoothViewModel.closeCar()
                    unLockingLogViewModel.insert(requireContext())
                    changeLockUnlockCarImageToClosed()
                    lockUnlockStatus = false

                }


            }

        }

    }

    private fun changeLockUnlockCarImageToOpen() {

        binding.lockUnlockCar.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_lock_open_24
            )
        )

    }

    private fun changeLockUnlockCarImageToClosed() {

        binding.lockUnlockCar.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_lock_24
            )
        )

    }

    private fun setUnLockingLogValues(isLock: Boolean) {

        unLockingLogViewModel.setCarName(arduinoBluetoothViewModel.carName.value!!)
        unLockingLogViewModel.setBluetoothMac(arduinoBluetoothViewModel.bluetoothMac.value!!)
        unLockingLogViewModel.setIsLock(isLock)

    }

}
