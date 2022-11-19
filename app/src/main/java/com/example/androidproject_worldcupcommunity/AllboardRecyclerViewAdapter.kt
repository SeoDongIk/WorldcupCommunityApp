package com.example.androidproject_worldcupcommunity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_write.view.*

class AllboardRecyclerViewAdapter(val context: Context, val items: MutableList<CaetegoryData>, val keyList: MutableList<String>, val starItems: MutableList<CaetegoryData>, val starKeyList: MutableList<String>) : RecyclerView.Adapter<AllboardRecyclerViewAdapter.ViewHolder>() {

    val database = Firebase.database
    val myRef = database.getReference("starcategory")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllboardRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.allboarditem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllboardRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position], keyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item : CaetegoryData, key : String){
            val categoryView = itemView.findViewById<TextView>(R.id.category_name)
            val checkView = itemView.findViewById<ImageView>(R.id.starBtn)
            categoryView.setText(item.category_name)

            categoryView.setOnClickListener {
                val intent = Intent(context, BoardActivity::class.java)
                intent.putExtra("category", item.category_name)
                itemView.context.startActivity(intent)
            }

            if(starKeyList.contains(key)){
                checkView.setImageResource(R.drawable.blackstar)
            }

            checkView.setOnClickListener {
                //Toast.makeText(context, starKeyList.toString(), Toast.LENGTH_SHORT).show()
                if(starKeyList.contains(key)){
                    checkView.setImageResource(R.drawable.star)
                    myRef.child(key).removeValue()
                }else{
                    checkView.setImageResource(R.drawable.blackstar)
                    myRef.child(key).setValue(CaetegoryData(true,item.category_name))
                }
            }

        }
    }

}