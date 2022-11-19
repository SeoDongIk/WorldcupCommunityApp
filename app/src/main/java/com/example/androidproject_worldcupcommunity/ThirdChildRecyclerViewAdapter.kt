package com.example.androidproject_worldcupcommunity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ThirdChildRecyclerViewAdapter(val context: Context, val items: MutableList<BoardModel>, val keyList: MutableList<String>) : RecyclerView.Adapter<ThirdChildRecyclerViewAdapter.ViewHolder>() {

    val database = Firebase.database
    val myRef = database.getReference("character")
    val characterItems = ArrayList<CharacterData>()
    val characterkeyList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdChildRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.boarditem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThirdChildRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position],keyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item : BoardModel, key :String){
            val title = itemView.findViewById<TextView>(R.id.title)
            val subtitle = itemView.findViewById<TextView>(R.id.subTitle)
            val commentNumberView = itemView.findViewById<TextView>(R.id.commentNumber)
            val imageView = itemView.findViewById<ImageView>(R.id.contentImage)
            val imageView2 = itemView.findViewById<CardView>(R.id.contentImage2)
            val storageReference = Firebase.storage.reference.child(key + ".png")

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
                            subtitle.setText(characteritem.charcter_name+" 후보 " + item.time + " 조회 25")
                            storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
                                if(task.isSuccessful) {
                                    Glide.with(context)
                                        .load(task.result)
                                        .into(imageView)
                                } else {
                                    imageView2.isVisible = false
                                }
                            })
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            }
            myRef.addValueEventListener(postListener)
            title.setText("["+item.category+"] " + item.title)



        }
    }

}