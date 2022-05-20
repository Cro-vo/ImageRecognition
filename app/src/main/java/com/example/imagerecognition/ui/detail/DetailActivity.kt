package com.example.imagerecognition.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.imagerecognition.databinding.ActivityDetailBinding
import com.example.imagerecognition.logic.model.AnimalResponse

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getParcelableExtra<AnimalResponse.BaikeInfo>("result")
        Log.d("result", "result in  activity ${result}")


    }
}