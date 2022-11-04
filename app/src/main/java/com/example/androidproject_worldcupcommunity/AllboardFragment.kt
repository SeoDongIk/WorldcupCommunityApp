package com.example.androidproject_worldcupcommunity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject_worldcupcommunity.databinding.FragmentAllboardBinding
import com.example.androidproject_worldcupcommunity.databinding.FragmentFirstBinding
import com.example.androidproject_worldcupcommunity.databinding.NavHeaderBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AllboardFragment : Fragment() {

    private lateinit var binding : FragmentAllboardBinding
    private lateinit var allboardRecyclerAdapter : AllboardRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 화면 생성

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_allboard, container, false)

        // 변수 설정

        val database = Firebase.database
        val myRef = database.getReference("category")
        val categoryItems = ArrayList<CaetegoryData>()
        val keyList = ArrayList<String>()
        val allboardrecyclerview = binding.allboardRecyclerView

        // 서버에서 카테고리 정보 받아오기 -> 실시간 동기화 해야함

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
                allboardRecyclerAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(postListener)

        // 카테고리 정보 화면에 표시하기 -> 실시간 동기화 해야함

        allboardRecyclerAdapter = AllboardRecyclerViewAdapter(categoryItems)
        allboardrecyclerview?.adapter = allboardRecyclerAdapter
        allboardrecyclerview?.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

}