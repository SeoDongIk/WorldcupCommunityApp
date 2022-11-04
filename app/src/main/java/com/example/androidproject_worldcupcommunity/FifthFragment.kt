package com.example.androidproject_worldcupcommunity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.androidproject_worldcupcommunity.databinding.FragmentFifthBinding
import com.example.androidproject_worldcupcommunity.databinding.FragmentFourthBinding

private lateinit var binding : FragmentFifthBinding

class FifthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fifth, container, false)

        binding.firstBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fifthFragment_to_firstFragment)
        }

        binding.secondBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fifthFragment_to_secondFragment)
        }

        binding.thirdBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fifthFragment_to_thirdFragment)
        }

        binding.fourBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fifthFragment_to_fourthFragment)
        }

        binding.fiveBtn.setOnClickListener {
            //
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}