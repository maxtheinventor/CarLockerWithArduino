package com.example.carlockerwitharduino.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.adapter.RegisteredCarsRVA
import com.example.carlockerwitharduino.adapter.UnLockLogRVA
import com.example.carlockerwitharduino.database.CarRegisterDatabase
import com.example.carlockerwitharduino.database.UnLockingLogDatabase
import com.example.carlockerwitharduino.databinding.FragmentLockingHistoryBinding
import com.example.carlockerwitharduino.factory.CarRegisterViewModelFactory
import com.example.carlockerwitharduino.factory.UnLockingLogViewModelFactory
import com.example.carlockerwitharduino.model.CarRegisterModel
import com.example.carlockerwitharduino.model.UnLockingLogModel
import com.example.carlockerwitharduino.repository.CarRegisterRepository
import com.example.carlockerwitharduino.repository.UnLockingLogRepository
import com.example.carlockerwitharduino.view_model.CarRegisterViewModel
import com.example.carlockerwitharduino.view_model.UnLockingLogViewModel


class LockingHistory : Fragment() {

    private lateinit var binding: FragmentLockingHistoryBinding
    private lateinit var adapter: UnLockLogRVA
    private lateinit var unLockingLogViewModel: UnLockingLogViewModel
    private lateinit var unLockingLogList: List<UnLockingLogModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_locking_history,
            container,
            false
        )

        initUnLockingLogViewModel()
        initRecyclerView()
        deleteAllUnLockingLogsButtonFunctionality()

        return binding.root
    }

    private fun initUnLockingLogViewModel() {

        val dao = UnLockingLogDatabase.getInstance(requireContext()).unLockingDAO
        val repository = UnLockingLogRepository(dao)
        val factory = UnLockingLogViewModelFactory(repository)

        unLockingLogViewModel = ViewModelProvider(this, factory)[UnLockingLogViewModel::class.java]

    }

    private fun initRecyclerView() {

        binding.apply {

            unLockingLogsRV.layoutManager = LinearLayoutManager(requireActivity())
            adapter = UnLockLogRVA(requireContext() )
            unLockingLogsRV.adapter = adapter
            displayLockingHistoryList()

        }

    }

    private fun displayLockingHistoryList() {

        unLockingLogViewModel.getAllUnLockingLogRegister.observe(requireActivity(), Observer {

            unLockingLogList = it
            showHideNoUnLockingLogsForAnyCarsTitle()
            adapter.setList(unLockingLogList)
            adapter.notifyDataSetChanged()

        })

    }

    private fun showHideNoUnLockingLogsForAnyCarsTitle() {

        binding.apply {

            if(unLockingLogList.isEmpty()) {

                noUnLockingLogsForAnyCarsTitle.visibility = View.VISIBLE
                deleteAllUnLockLogs.visibility = View.GONE

            } else if(unLockingLogList.isNotEmpty()) {

                noUnLockingLogsForAnyCarsTitle.visibility = View.GONE
                deleteAllUnLockLogs.visibility = View.VISIBLE

            }

        }

    }

    private fun deleteAllUnLockingLogsButtonFunctionality() {

        binding.apply {

            deleteAllUnLockLogs.setOnClickListener {
                deleteAllUnlockingLogsAlertDialog()
            }

        }

    }

    private fun deleteAllUnlockingLogsAlertDialog() {

        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle(R.string.alertDeletingAllData)
        alertDialogBuilder.setMessage("You are about to delete all of the un/locking logs from the " +
                "database. \nAre you sure of that?")


        alertDialogBuilder.setPositiveButton(R.string.yes){_,_ ->
            unLockingLogViewModel.deleteAll()
        }
        alertDialogBuilder.setNegativeButton(R.string.no){_,_ ->

        }

        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.show()

    }


}