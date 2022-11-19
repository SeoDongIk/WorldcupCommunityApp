package com.example.androidproject_worldcupcommunity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidproject_worldcupcommunity.databinding.ActivityWriteBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay
import java.io.ByteArrayOutputStream


class WriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWriteBinding
    lateinit var myRef : DatabaseReference
    lateinit var myRef2 : DatabaseReference
    private var isImageUpload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 변수 설정

        val database = Firebase.database
        myRef = database.getReference("category")
        myRef2 = database.getReference("board")
        val items = ArrayList<CaetegoryData>()
        val itemss = ArrayList<String>()
        val itemKeyList = ArrayList<String>()
        var category = String()

        // 백 버튼 만들기

        binding.backBtn.setOnClickListener {
            finish()
        }

        // 스피너 만들기

       myRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    items.add(snapshot.getValue(CaetegoryData::class.java)!!)
                    Log.d("spinner_test", snapshot.getValue(CaetegoryData::class.java).toString())
                    itemKeyList.add(snapshot.key.toString())
                }
                Log.d("spinner_test", items.toString())
                for(item in items){
                    if(item.exist == true){
                        item.category_name?.let { itemss.add(it) }
                    }
                }
                val adapter = ArrayAdapter(baseContext, R.layout.spinner_item, itemss)
                binding.categorySpinner.adapter = adapter

                binding.categorySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position != 0) Toast.makeText(this@WriteActivity, itemss[position], Toast.LENGTH_SHORT).show()
                        category = itemss[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        // 등록 버튼

        binding.submitBtn.setOnClickListener {

            var isGoToSubmit = true

            val title = binding.title.text.toString()
            val content = binding.content.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            if(title.isEmpty()) {
                Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToSubmit = false
            }

            if(content.isEmpty()) {
                Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToSubmit = false
            }

            if(isGoToSubmit){
                val key = myRef2.push().key.toString()
                myRef2
                    .child(key)
                    .setValue(BoardModel(title, content, uid, time, category))
                Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_LONG).show()
                if(isImageUpload == true) {
                    imageUpload(key)
                }
                finish()
            }
        }

        // 맨 아래에 있는 뷰 버튼 리스너

        binding.icon1.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true
        }

        binding.icon2.setOnClickListener {
            //
        }

        binding.icon3.setOnClickListener {
            //
        }

        binding.icon4.setOnClickListener {
            //
        }

        binding.icon5.setOnClickListener {
            //
        }

        binding.icon6.setOnClickListener {
            //
        }

        binding.icon7.setOnClickListener {
            //
        }

        binding.icon8.setOnClickListener {
            //
        }
    }

    private fun imageUpload(key : String){
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")

        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            binding.imageArea.setImageURI(data?.data)
        }
    }

}