package com.example.imagerecognition.ui.animal_plant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagerecognition.ui.mainPage.MainActivity
import com.example.imagerecognition.databinding.ActivityAnimalBinding
import com.example.imagerecognition.logic.LoadingDialog

class AnimalActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimalBinding

    val animalViewModel by lazy { ViewModelProvider(this).get(AnimalViewModel::class.java) }
    val plantViewModel by lazy { ViewModelProvider(this).get(PlantViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        LoadingDialog.getInstance(this).show()

        val base64 = intent.getStringExtra("base64")
        if (base64 != null) {
            getInfo(base64)
        }


    }


    // 向服务器发起请求
    private fun getInfo(base64: String) {
        val function = intent.getIntExtra("function", 1)
        if (function == MainActivity.ANIMAL){
            animalViewModel.getAnimalInfo(base64)
//            Log.d("base64", base64.length.toString())
            animalViewModel.animalLiveData.observe(this, Observer {
                val results = it.getOrNull()
//                Log.d("base_results",results.toString())

                val layoutManager = LinearLayoutManager(this)
                binding.recyclerView.layoutManager = layoutManager
                if (results != null) {
                    // 请求成功
//                        Log.d("result", "results...${results}")
                    val adapter = AnimalAdapter(this, results)
                    binding.recyclerView.adapter = adapter


                    LoadingDialog.getInstance(this).hide()
                    LoadingDialog.setInstance(null)

                } else {
                    // 请求失败
                    Toast.makeText(this, "请求失败，请重新尝试", Toast.LENGTH_SHORT).show()

                }


            })

        } else if (function == MainActivity.PLANT) {
//            Log.d("plant", "in plant")

            plantViewModel.getPlantInfo(base64)
//            Log.d("base64", base64.length.toString())
            plantViewModel.plantLiveData.observe(this, Observer {
                val results = it.getOrNull()
//                Log.d("base_results",results.toString())

                val layoutManager = LinearLayoutManager(this)
                binding.recyclerView.layoutManager = layoutManager
                if (results != null) {
                    // 请求成功
//                        Log.d("result", "results...${results}")
                    val adapter = PlantAdapter(this, results)
                    binding.recyclerView.adapter = adapter


                    LoadingDialog.getInstance(this).hide()
                    LoadingDialog.setInstance(null)

                } else {
                    // 请求失败
                    Toast.makeText(this, "请求失败，请重新尝试", Toast.LENGTH_SHORT).show()

                }


            })


        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> this.finish()
        }

        return super.onOptionsItemSelected(item)
    }


}