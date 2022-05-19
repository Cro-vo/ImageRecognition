package com.example.imagerecognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagerecognition.databinding.ActivityMainBinding
import com.example.imagerecognition.logic.network.ImageRecognitionNetwork
import com.example.imagerecognition.ui.MainPage.FunctionAdapter
import com.example.imagerecognition.ui.MainPage.MainPageViewModel
import com.example.imagerecognition.ui.animal.AnimalViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val viewModel by lazy { ViewModelProvider(this).get(AnimalViewModel::class.java) }

    val mainpageViewModel by lazy { ViewModelProvider(this).get(MainPageViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = FunctionAdapter(this, mainpageViewModel.functionList)
        binding.recyclerView.adapter = adapter


//        binding.button.setOnClickListener {
//            viewModel.getAnimalInfo("https://baidu-ai.bj.bcebos.com/image-classify/animal.jpeg")
//        }
//
//
//        viewModel.animalLiveData.observe(this, Observer {
//            val results = it.getOrNull()
//            println(results)
//        })



    }


}