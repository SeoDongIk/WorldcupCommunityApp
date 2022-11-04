package com.example.androidproject_worldcupcommunity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject_worldcupcommunity.databinding.FragmentFirstBinding
import com.example.androidproject_worldcupcommunity.databinding.NavHeaderBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient

class FirstFragment : Fragment() {

    private lateinit var binding : FragmentFirstBinding
    private lateinit var callback: OnBackPressedCallback

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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)

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

        // 버튼 관련

        binding.firstBtn.setOnClickListener {
            //
        }

        binding.secondBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }

        binding.thirdBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_firstFragment_to_thirdFragment)
        }

        binding.fourBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_firstFragment_to_fourthFragment)
        }

        binding.fiveBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_firstFragment_to_fifthFragment)
        }

        // 탭 레이아웃, child Fragment 조절

        binding.tabBtn1.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,firstChildFragment).commit()
            binding.tabColorBtn1.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            binding.tabColorBtn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tabColorBtn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tabColorBtn1.setTypeface(binding.tabColorBtn1.typeface, Typeface.BOLD)
            binding.tabColorBtn2.setTypeface(binding.tabColorBtn2.typeface, Typeface.NORMAL)
            binding.tabColorBtn3.setTypeface(binding.tabColorBtn3.typeface, Typeface.NORMAL)
            binding.tabColorUnderBtn1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            binding.tabColorUnderBtn2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            binding.tabColorUnderBtn3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
        }

        binding.tabBtn2.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,secondChildFragment).commit()
            binding.tabColorBtn1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tabColorBtn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            binding.tabColorBtn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tabColorBtn1.setTypeface(binding.tabColorBtn1.typeface, Typeface.NORMAL)
            binding.tabColorBtn2.setTypeface(binding.tabColorBtn2.typeface, Typeface.BOLD)
            binding.tabColorBtn3.setTypeface(binding.tabColorBtn3.typeface, Typeface.NORMAL)
            binding.tabColorUnderBtn1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            binding.tabColorUnderBtn2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            binding.tabColorUnderBtn3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
        }

        binding.tabBtn3.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,thirdChildFragment).commit()
            binding.tabColorBtn1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tabColorBtn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tabColorBtn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            binding.tabColorBtn1.setTypeface(binding.tabColorBtn1.typeface, Typeface.NORMAL)
            binding.tabColorBtn2.setTypeface(binding.tabColorBtn2.typeface, Typeface.NORMAL)
            binding.tabColorBtn3.setTypeface(binding.tabColorBtn3.typeface, Typeface.BOLD)
            binding.tabColorUnderBtn1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            binding.tabColorUnderBtn2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkred))
            binding.tabColorUnderBtn3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
        }

        // 글쓰기 버튼

        binding.fabReward.setOnClickListener {
            val intent = Intent(context, WriteActivity::class.java)
            startActivity(intent)
        }

        // 캐릭터 설정

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
            starboardBtn.setTextColor(ContextCompat.getColor(requireContext(),
                android.R.color.tertiary_text_light
            ))
            starboardLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView3,allboardFragment).commit()
        }

        starboardBtn.setOnClickListener {
            allboardBtn.setTextColor(ContextCompat.getColor(requireContext(),
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

    var mBackWait : Long = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(binding.drawerlayout.isDrawerOpen(GravityCompat.START)){
                    binding.drawerlayout.closeDrawers()
                }else{
                    if(System.currentTimeMillis() - mBackWait >=2000 ) {
                        var view : View = binding.drawerlayout
                        mBackWait = System.currentTimeMillis()
                        Snackbar.make(view,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Snackbar.LENGTH_LONG).show()
                    } else {
                        activity?.finishAffinity()
                    }
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

fun Activity.setStatusBarTransparent() {
    window.apply {
        setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
    if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}

fun Activity.setStatusBarOrigin() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
    if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}

fun getStatusBarHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

fun getNaviBarHeight(context: Context): Int {
    val resourceId: Int = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}