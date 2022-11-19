package com.example.androidproject_worldcupcommunity

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.androidproject_worldcupcommunity.databinding.FragmentBoardFirstBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class BoardFirstFragment : Fragment() {

    private lateinit var binding : FragmentBoardFirstBinding
    private lateinit var callback: OnBackPressedCallback
    val database = Firebase.database
    val myRef = database.getReference("character")
    val characterItems = ArrayList<CharacterData>()
    val characterkeyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(context,"성공", Toast.LENGTH_SHORT).show()
        when(item!!.itemId){
            android.R.id.home -> {
                binding.drawerlayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 화면 생성

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_first, container, false)

        // 변수 설정

        val firstChildFragment = FirstChildFragment()
        val secondChildFragment = SecondChildFragment()
        val thirdChildFragment = ThirdChildFragment()
        val allboardFragment = AllboardFragment()
        val starboardFragment = StarboardFragment()

        // 툴바 설정

        val toolbar = binding.toolbar

        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)


        // 탭 레이아웃, child Fragment 조절

        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,secondChildFragment).commit()
        binding.tabBtn2.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,secondChildFragment).commit()
            binding.tabColorBtn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            binding.tabColorBtn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tabColorBtn2.setTypeface(binding.tabColorBtn2.typeface, Typeface.BOLD)
            binding.tabColorBtn3.setTypeface(binding.tabColorBtn3.typeface, Typeface.NORMAL)
            binding.tabColorUnderBtn2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            binding.tabColorUnderBtn3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
        }

        binding.tabBtn3.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,thirdChildFragment).commit()
            binding.tabColorBtn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tabColorBtn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            binding.tabColorBtn2.setTypeface(binding.tabColorBtn2.typeface, Typeface.NORMAL)
            binding.tabColorBtn3.setTypeface(binding.tabColorBtn3.typeface, Typeface.BOLD)
            binding.tabColorUnderBtn2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            binding.tabColorUnderBtn3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
        }

        // 글쓰기 버튼

        binding.fabReward.setOnClickListener {
            val intent = Intent(context, WriteActivity::class.java)
            startActivity(intent)
        }

        // 캐릭터 설정

        var Cimage = binding.drawerlayout.findViewById<CircleImageView>(R.id.characterImage)
        var Cname = binding.drawerlayout.findViewById<TextView>(R.id.characterName)

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                characterItems.clear()
                characterkeyList.clear()
                for(dataModel in snapshot.children){
                    //Log.d("log",dataModel.toString())
                    //Log.d("log",dataModel.value.toString())
                    //Log.d("key",dataModel.key.toString())
                    val key = dataModel.key.toString()
                    val item = dataModel.getValue(CharacterData::class.java)
                    characterItems.add(item!!)
                    characterkeyList.add(key)
                }
                Toast.makeText(context, characterItems.toString(), Toast.LENGTH_SHORT).show()
                for(characteritem in characterItems){
                    if(characteritem.uid == FBAuth.getUid()){
                        Cname.setText(characteritem.charcter_name)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(postListener)

        val characterBtn = binding.drawerlayout.findViewById<LinearLayout>(R.id.characterBtn)

        characterBtn.setOnClickListener {
            val intent = Intent(context, CharacterActivity::class.java)
            startActivity(intent)
        }

        // 전체 게시판, 즐겨찾는 게시판

        val allboardBtn = binding.drawerlayout.findViewById<TextView>(R.id.allboardBtn)
        val starboardBtn = binding.drawerlayout.findViewById<TextView>(R.id.starboardBtn)
        val allboardLayout = binding.drawerlayout.findViewById<LinearLayout>(R.id.allboardLayout)
        val starboardLayout = binding.drawerlayout.findViewById<LinearLayout>(R.id.starboardLayout)

        allboardBtn.setOnClickListener {
            allboardBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            allboardLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            starboardBtn.setTextColor(
                ContextCompat.getColor(requireContext(),
                android.R.color.tertiary_text_light
            ))
            starboardLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView3,allboardFragment).commit()
        }

        starboardBtn.setOnClickListener {
            allboardBtn.setTextColor(
                ContextCompat.getColor(requireContext(),
                android.R.color.tertiary_text_light
            ))
            allboardLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            starboardBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            starboardLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView3,starboardFragment).commit()
        }

        // 카테고리 설정 버튼

        val categoryBtn = binding.drawerlayout.findViewById<TextView>(R.id.categoryBtn)

        categoryBtn.setOnClickListener {
            activity?.finishAffinity()
            val intent = Intent(context, CategoryActivity::class.java)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setStatusBarTransparent()
        binding.toolbar.setPadding(0, getStatusBarHeight(requireContext()),0,0)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(binding.drawerlayout.isDrawerOpen(GravityCompat.START)){
                    binding.drawerlayout.closeDrawers()
                }else{
                    activity?.finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()

        callback.remove()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // requireActivity().setStatusBarOrigin()
    }
}