package com.example.androidproject_worldcupcommunity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.androidproject_worldcupcommunity.databinding.FragmentFirstBinding
import com.example.androidproject_worldcupcommunity.databinding.FragmentSecondBinding

private lateinit var binding : FragmentSecondBinding

class SecondFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)

        binding.firstBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }

        binding.secondBtn.setOnClickListener {
            //
        }

        binding.thirdBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
        }

        binding.fourBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_secondFragment_to_fourthFragment)
        }

        binding.fiveBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_secondFragment_to_fifthFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}