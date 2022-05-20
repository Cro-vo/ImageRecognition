package com.example.imagerecognition.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.imagerecognition.databinding.ActivityDetailBinding
import com.example.imagerecognition.logic.model.AnimalResponse
import com.example.imagerecognition.logic.model.PlantResponse
import com.example.imagerecognition.ui.mainPage.MainActivity

class DetailActivity : AppCompatActivity() {


    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val function = intent.getIntExtra("function", 1)

        if (function == MainActivity.ANIMAL){
            val result = intent.getParcelableExtra<AnimalResponse.Result>("result")!!
            Glide.with(this).load(result!!.baikeInfo.imageURL).into(binding.imageView)
            binding.name.text = result.name
            binding.score.text = "相似度：${result.score}"
            binding.description.text = result.baikeInfo.description

        } else if (function == MainActivity.PLANT){
            Log.d("plant", "here")
            val result = intent.getParcelableExtra<PlantResponse.Result>("result")
            Glide.with(this).load(result!!.baikeInfo.imageURL).into(binding.imageView)
            binding.name.text = result.name
            binding.score.text = "相似度：${result.score}"
            binding.description.text = result.baikeInfo.description

        }


//        Log.d("result", "result in  activity ${result}")



    }
}