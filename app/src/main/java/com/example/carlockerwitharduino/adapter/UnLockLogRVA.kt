package com.example.carlockerwitharduino.adapter

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.databinding.RegisteredCarRowBinding
import com.example.carlockerwitharduino.databinding.UnLockingLogRowBinding
import com.example.carlockerwitharduino.model.CarRegisterModel
import com.example.carlockerwitharduino.model.UnLockingLogModel

class UnLockLogRVA(context:Context) : RecyclerView.Adapter<UnLockingLogViewHolder>() {

    private val unLockingLogsArrayList = ArrayList<UnLockingLogModel>()
    private val context:Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnLockingLogViewHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: UnLockingLogRowBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.un_locking_log_row, parent, false)
        return UnLockingLogViewHolder(binding)

    }

    override fun onBindViewHolder(holder: UnLockingLogViewHolder, position: Int) {
        holder.bind(unLockingLogsArrayList[position],context)
    }

    override fun getItemCount(): Int {
        return unLockingLogsArrayList.size
    }

    fun setList(unLockingLogModel: List<UnLockingLogModel>)  {

        unLockingLogsArrayList.clear()
        unLockingLogsArrayList.addAll(unLockingLogModel)

    }

}

class UnLockingLogViewHolder(val binding: UnLockingLogRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(unLockingLogModel: UnLockingLogModel,context: Context) {

        binding.apply {

            carNameUnLockLog.text = unLockingLogModel.carName
            bluetoothMacUnLockLog.text = unLockingLogModel.bluetoothMac
            dateUnLockLog.text = unLockingLogModel.unLockingDate
            timeUnLockLog.text = unLockingLogModel.unLockingHour

            if(unLockingLogModel.isLock) {
                unLockingLogCardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.carLocked))
            } else {
                unLockingLogCardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.carUnlocked))
            }


        }

    }

}