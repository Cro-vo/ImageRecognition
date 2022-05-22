package com.example.imagerecognition.ui.mainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagerecognition.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        val ANIMAL: Int = 1
        val PLANT: Int = 2

    }

    lateinit var binding: ActivityMainBinding

    val mainpageViewModel by lazy { ViewModelProvider(this).get(MainPageViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = FunctionAdapter(this, mainpageViewModel.functionList)
        binding.recyclerView.adapter = adapter





    }


}