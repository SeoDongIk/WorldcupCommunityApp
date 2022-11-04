package com.example.androidproject_worldcupcommunity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryRecyclerViewAdapter(val items : MutableList<CaetegoryData>) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categoryitem,parent,false)
        return ViewHolder(view)
    }

    interface ItemClick{
        fun onClick(view: View, position : Int)
    }

    var itemClick : ItemClick? = null

    override fun onBindViewHolder(holder: CategoryRecyclerViewAdapter.ViewHolder, position: Int) {
        if(itemClick != null){
            holder.itemView.findViewById<Button>(R.id.removeBtn).setOnClickListener {
                itemClick?.onClick(it, position)
            }
        }
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item : CaetegoryData){
            val categoryView = itemView.findViewById<TextView>(R.id.category_name)
            categoryView.setText(item.category_name)
        }
    }

}