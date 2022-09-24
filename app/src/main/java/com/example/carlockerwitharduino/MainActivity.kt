package com.example.carlockerwitharduino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.carlockerwitharduino.databinding.ActivityMainBinding
import com.example.carlockerwitharduino.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        menuNavigationFragmentReplacement(CarConnectionAndControl())
        bottomNavigationFunctionality()

    }

    private fun bottomNavigationFunctionality() {

        binding.bottomNavigationViewAM.setOnItemSelectedListener {

            when(it.itemId) {

                R.id.controlCarBN -> menuNavigationFragmentReplacement(CarConnectionAndControl())
                R.id.unLockingLogBN -> menuNavigationFragmentReplacement(LockingHistory())
                R.id.aboutBN -> menuNavigationFragmentReplacement(About())
                R.id.registeredCarsBN -> menuNavigationFragmentReplacement(RegisteredCars())

                else -> {}

            }

            true

        }

    }

    private fun menuNavigationFragmentReplacement(fragment: Fragment) {

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayoutAM,fragment)
            fragmentTransaction.commit()

    }


}