package com.example.androidproject_worldcupcommunity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.androidproject_worldcupcommunity.databinding.FragmentFourthBinding
import com.example.androidproject_worldcupcommunity.databinding.FragmentThirdBinding

private lateinit var binding : FragmentFourthBinding

class FourthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container, false)

        binding.firstBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fourthFragment_to_firstFragment)
        }

        binding.secondBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fourthFragment_to_secondFragment)
        }

        binding.thirdBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fourthFragment_to_thirdFragment)
        }

        binding.fourBtn.setOnClickListener {
            //
        }

        binding.fiveBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fourthFragment_to_fifthFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}