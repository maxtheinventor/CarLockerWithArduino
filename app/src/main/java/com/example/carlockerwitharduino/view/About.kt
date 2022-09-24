package com.example.carlockerwitharduino.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.carlockerwitharduino.R
import com.example.carlockerwitharduino.databinding.FragmentAboutBinding
import com.example.carlockerwitharduino.util.GlobalFunctions

class About : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_about,
            container,
            false
        )

        takeUsersToInstagramAccount()
        takeUsersToMaxTheInventorWebPage()

        return binding.root

    }

    private fun takeUsersToInstagramAccount() {

        binding.instagramAccount.setOnClickListener {
            GlobalFunctions.takeUsersToMaxTheInventorInstagramAccount(requireContext())
        }

    }

    private fun takeUsersToMaxTheInventorWebPage() {

        binding.webPage.setOnClickListener {
            GlobalFunctions.takeUsersToMaxTheInventorWebSite(requireContext())
        }

    }

}