package com.example.androidproject_worldcupcommunity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.androidproject_worldcupcommunity.databinding.ActivityCharacterBinding
import com.example.androidproject_worldcupcommunity.databinding.ActivityWriteBinding
import com.example.androidproject_worldcupcommunity.databinding.FragmentAllboardBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CharacterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCharacterBinding
    val database = Firebase.database
    val myRef = database.getReference("character")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.submitBtn.setOnClickListener {
            var isGoToSubmit = true

            val name = binding.charcterName.text.toString()
            val uid = FBAuth.getUid()

            if(title.isEmpty()) {
                Toast.makeText(this, "별명을 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToSubmit = false
            }

            if(isGoToSubmit){
                myRef
                    .child(uid)
                    .setValue(CharacterData(uid, name))
                finish()
            }

        }

    }
}