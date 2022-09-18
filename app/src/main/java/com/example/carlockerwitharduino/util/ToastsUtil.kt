package com.example.carlockerwitharduino.util

import android.content.Context
import android.widget.Toast
import com.example.carlockerwitharduino.R

class ToastsUtil {

    companion object {

        fun yourDeviceDoesntSupportBluetooth(context: Context) {
            Toast.makeText(context, R.string.yourDeviceDoesntSupportBluetooth,Toast.LENGTH_SHORT).show()
        }

        fun fieldsCantBeEmptyToContinue(context: Context) {
            Toast.makeText(context,R.string.fieldsCantBeEmpty,Toast.LENGTH_SHORT).show()
        }

        fun carRegisterDatabaseAction(context: Context, message: String) {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

    }

}