package com.example.carlockerwitharduino.util

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.example.carlockerwitharduino.MainActivity
import com.example.carlockerwitharduino.R

class GlobalFunctions {

    companion object {

        fun enableBluetoothToContinue(context: Context) {

            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(R.string.cantConnectToCar)
            alertDialogBuilder.setMessage(
                "You must have bluetooth enabled to be able to connect" +
                        " to the car and control it"
            )
            alertDialogBuilder.setPositiveButton("OPEN BLUETOOTH SETTINGS") { dialog, _ ->
                dialog.dismiss()
                context.startActivity(Intent().setAction(Settings.ACTION_BLUETOOTH_SETTINGS))
            }
            alertDialogBuilder.setNegativeButton("CANCEL") { _, _ -> }

            alertDialogBuilder.setCancelable(false)

            alertDialogBuilder.create().show()

        }

        fun takeUserToCarControl(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }

    }

}