package com.example.androidproject_worldcupcommunity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject_worldcupcommunity.databinding.FragmentAllboardBinding
import com.example.androidproject_worldcupcommunity.databinding.FragmentFirstBinding
import com.example.androidproject_worldcupcommunity.databinding.FragmentFirstChildBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirstChildFragment : Fragment() {

    private lateinit var binding : FragmentFirstChildBinding
    private lateinit var firstChildRecyclerAdapter : FirstChildRecyclerViewAdapter

    val database = Firebase.database
    val myRef = database.getReference("board")
    val boardList = mutableListOf<BoardModel>()
    val boardKeyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_child, container, false)

        val recyclerview = binding.firstRecyclerView

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                boardList.clear()
                boardKeyList.clear()
                for(dataModel in snapshot.children){
                    //Log.d("log",dataModel.toString())
                    //Log.d("log",dataModel.v2alue.toString())
                    //Log.d("key",dataModel.key.toString())
                    val key = dataModel.key.toString()
                    val item = dataModel.getValue(BoardModel::class.java)
                    boardList.add(item!!)
                    boardKeyList.add(key)
                }
                firstChildRecyclerAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(postListener)

        firstChildRecyclerAdapter = FirstChildRecyclerViewAdapter(requireContext(), boardList.asReversed(), boardKeyList.asReversed())
        recyclerview?.adapter = firstChildRecyclerAdapter
        recyclerview?.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

}