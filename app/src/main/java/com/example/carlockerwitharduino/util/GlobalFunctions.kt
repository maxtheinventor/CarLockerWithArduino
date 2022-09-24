package com.example.carlockerwitharduino.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.carlockerwitharduino.MainActivity
import com.example.carlockerwitharduino.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


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

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDate(): String {

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatted = current.format(formatter)
            return formatted.toString()

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentHourAndMinutes():String {

            val date = Date()

            val formatter = SimpleDateFormat("HH:mm")
            formatter.timeZone = TimeZone.getTimeZone("GMT+2")

            val formatted = formatter.format(date)
            return formatted.toString();

        }

        fun nfcFunctionalityNotAvailableYet(context: Context) {

            val alertDialogBuilder = AlertDialog.Builder(context)

            alertDialogBuilder.setTitle(R.string.functionalityNotAvailableYet)
            alertDialogBuilder.setMessage("The NFC functionality to open the car is not available yet." +
                    "\nSorry for the inconvenience.")

            alertDialogBuilder.setPositiveButton("OK"){_,_ ->}
            alertDialogBuilder.setCancelable(false)
            alertDialogBuilder.create().show()

        }

        fun takeUsersToMaxTheInventorInstagramAccount(context: Context) {

            val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.instagram.com/maxtheinventorofficial/"))
            context.startActivity(intent)

        }

        fun takeUsersToMaxTheInventorWebSite(context: Context) {

            val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://maxtheinventor.com/"))
            context.startActivity(intent)

        }

    }

}