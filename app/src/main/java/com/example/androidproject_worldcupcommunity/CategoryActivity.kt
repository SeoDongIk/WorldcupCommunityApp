package com.example.androidproject_worldcupcommunity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject_worldcupcommunity.databinding.ActivityCategoryBinding
import com.example.androidproject_worldcupcommunity.databinding.EdittextdialogBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import retrofit2.http.POST

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCategoryBinding
    private lateinit var categoryRecyclerAdapter : CategoryRecyclerViewAdapter

    val positiveButtonClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(baseContext,"Positive",Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(baseContext, "Positive", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 변수 설정

        val database = Firebase.database
        val myRef = database.getReference("category")
        val categoryItems = ArrayList<CaetegoryData>()
        val keyList = ArrayList<String>()
        val categoryrecyclerview = binding.recyclerview1

        // 카테고리 추가 버튼

        binding.categoryplusBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val builderItem = EdittextdialogBinding.inflate(layoutInflater)
            val editText = builderItem.edittext

            with(builder){
                setMessage("카테고리 이름을 입력하세요")
                setView(builderItem.root)
                setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->
                    if(editText.text != null){
                        // 서버로 카테고리 정보 보내기
                        myRef.push().setValue(CaetegoryData(true,"${editText.text}"))
                    }
                }
                show()
            }
            categoryRecyclerAdapter.notifyDataSetChanged()
        }

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
                categoryRecyclerAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(postListener)

        // 카테고리 정보 화면에 표시하기 -> 실시간 동기화 해야함

        categoryRecyclerAdapter = CategoryRecyclerViewAdapter(categoryItems)
        categoryrecyclerview.adapter = categoryRecyclerAdapter
        categoryrecyclerview.layoutManager = LinearLayoutManager(this)

        // 카테고리 정보 서버에서 삭제하기 -> 화면에 표시된 카테고리만 삭제할 수 있어야함.

        categoryRecyclerAdapter.itemClick = object : CategoryRecyclerViewAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                Toast.makeText(baseContext,categoryItems[position].category_name,Toast.LENGTH_SHORT).show()
                myRef.child(keyList[position]).removeValue()
            }
        }

    }
}