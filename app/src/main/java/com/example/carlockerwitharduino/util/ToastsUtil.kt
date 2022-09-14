package com.example.carlockerwitharduino.util

import android.content.Context
import android.widget.Toast
import com.example.carlockerwitharduino.R

class ToastsUtil {

    companion object {

        fun yourDeviceDoesntSupportBluetooth(context: Context) {
            Toast.makeText(context, R.string.yourDeviceDoesntSupportBluetooth,Toast.LENGTH_SHORT).show()
        }

    }

}