package com.example.androidproject_worldcupcommunity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject_worldcupcommunity.databinding.FragmentAllboardBinding
import com.example.androidproject_worldcupcommunity.databinding.FragmentStarboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StarboardFragment : Fragment() {

    private lateinit var binding : FragmentStarboardBinding
    private lateinit var starboardRecyclerAdapter : StarboardRecyclerViewAdapter
    val database = Firebase.database
    val myRef = database.getReference("starcategory")
    val categoryItems = ArrayList<CaetegoryData>()
    val keyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 화면 생성

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_starboard, container, false)

        // 변수 설정

        val starboardrecyclerview = binding.starboardRecyclerView

        // 서버에서 즐겨찾기 카테고리 정보 받아오기 -> 실시간 동기화 해야함

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryItems.clear()
                keyList.clear()
                for(dataModel in snapshot.children){
                    //Log.d("log",dataModel.toString())
                    //Log.d("log",dataModel.value.toString())
                    //Log.d("key",dataModel.key.toString())
                    val key = dataModel.key.toString()
                    val item = dataModel.getValue(CaetegoryData::class.java)
                    categoryItems.add(item!!)
                    keyList.add(key)
                }
                starboardRecyclerAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(postListener)

        // 카테고리 정보 화면에 표시하기 -> 실시간 동기화 해야함

        starboardRecyclerAdapter = StarboardRecyclerViewAdapter(requireContext(), categoryItems,keyList)
        starboardrecyclerview?.adapter = starboardRecyclerAdapter
        starboardrecyclerview?.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

}